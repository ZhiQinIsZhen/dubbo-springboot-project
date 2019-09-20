package com.liyz.service.third.handler.abs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.liyz.common.base.util.CommonConverterUtil;
import com.liyz.common.base.util.SpringContextUtil;
import com.liyz.service.third.analysis.AnalysisFactoryUtil;
import com.liyz.service.third.analysis.IAnalysis;
import com.liyz.service.third.bo.PageBO;
import com.liyz.service.third.config.AsynThreadPoolConfig;
import com.liyz.service.third.config.ElasticSearchConfig;
import com.liyz.service.third.config.ThirdServiceUrlConfig;
import com.liyz.service.third.constant.ThirdType;
import com.liyz.service.third.dao.ThirdDataMapper;
import com.liyz.service.third.handler.service.ThirdService;
import com.liyz.service.third.model.ThirdDataDO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/17 16:39
 */
@Slf4j
public abstract class AbstractThirdService implements ThirdService {

    private static final int DEFAULT_PAGE_NO = 1;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private ThirdDataMapper thirdDataMapper;
    private RestTemplate restTemplate;
    @Getter
    private ThirdServiceUrlConfig thirdServiceUrlConfig;
    @Getter
    private ThreadLocal<Pair<Integer, Integer>> pageNoSize = new ThreadLocal<>();
    @Setter
    private boolean loadDataSource = true;

    public AbstractThirdService() {
        thirdDataMapper = SpringContextUtil.getBean("thirdDataMapper", ThirdDataMapper.class);
        if (thirdDataMapper == null) {
            throw new NoSuchBeanDefinitionException("no bean thirdDataMapper");
        }
        restTemplate = SpringContextUtil.getBean("restTemplate", RestTemplate.class);
        if (restTemplate == null) {
            throw new NoSuchBeanDefinitionException("no bean restTemplate");
        }
        thirdServiceUrlConfig = SpringContextUtil.getBean("thirdServiceUrlConfig", ThirdServiceUrlConfig.class);
        if (thirdServiceUrlConfig == null) {
            throw new NoSuchBeanDefinitionException("no bean thirdServiceUrlConfig");
        }
    }

    @Override
    public Object query(Map<String, String> heads, Map<String, Object> params, Map<String, Object> body,
                        ThirdType thirdType) throws Exception {
        return query(heads, params, body, HttpMethod.POST, thirdType);
    }

    @Override
    public Object query(Map<String, String> heads, Map<String, Object> params, Map<String, Object> body,
                        HttpMethod method, ThirdType thirdType) throws Exception {
        //检查参数
        checkParams(params, thirdType);
        //是否先从数据库load（后续可能再加一个es）
        if (loadDataSource) {
            List<ThirdDataDO> list = queryDataSource(params, thirdType, DEFAULT_PAGE_NO, 1);
            if (!CollectionUtils.isEmpty(list)) {
                return JSON.parseObject(list.get(0).getValue());
            }
        }
        pageNoSize.set(Pair.of(DEFAULT_PAGE_NO, 1));
        //获取请求url
        String thirdServiceUrl = setThirdServiceUrl(thirdType);
        //第三方查询
        String queryStr = doQuery(heads, params, body, method == null ? HttpMethod.POST : method, thirdServiceUrl);
        //解析数据
        Pair<List<JSONObject>, PageBO> pair = analysisResult(queryStr, thirdType);
        List<JSONObject> resultList = pair.getLeft();
        if (!CollectionUtils.isEmpty(resultList)) {
            return null;
        }
        //保存es，数据库（可以做成异步）
        saveEsData(resultList, thirdType);
        saveDataSource(resultList, thirdType);
        return resultList.get(0);
    }

    @Override
    public PageInfo<Object> queryPage(Map<String, String> heads, Map<String, Object> params, Map<String, Object> body,
                                  ThirdType thirdType, int pageNo) throws Exception {
        return queryPage(heads, params, body, HttpMethod.POST, thirdType, pageNo, DEFAULT_PAGE_SIZE);
    }

    @Override
    public PageInfo<Object> queryPage(Map<String, String> heads, Map<String, Object> params, Map<String, Object> body,
                                      HttpMethod method, ThirdType thirdType, int pageNo, int pageSize) throws Exception {
        //检查参数
        checkParams(params, thirdType);
        pageNo = pageNo <= 0 ? DEFAULT_PAGE_NO : pageNo;
        pageSize = pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
        pageNoSize.set(Pair.of(pageNo, pageSize));
        //增加分页查询参数
        addPageInfoToParams(params, pageNo, pageSize);
        //是否先从数据库load（后续可能再加一个es）
        if (loadDataSource) {
            List<ThirdDataDO> doList = queryDataSource(params, thirdType, pageNo, pageSize);
            if (!CollectionUtils.isEmpty(doList)) {
                List<Object> objects = doList.stream().map(thirdDataDO -> JSON.parseObject(thirdDataDO.getValue()))
                        .collect(Collectors.toList());
                PageInfo<ThirdDataDO> doPageInfo = new PageInfo<>(doList);
                Page<Object> page = new Page<>();
                BeanUtils.copyProperties(doPageInfo, page);
                page.clear();
                page.addAll(objects);
                return new PageInfo<>(page);
            }
        }
        //获取请求url
        String thirdServiceUrl = setThirdServiceUrl(thirdType);
        //第三方查询
        String queryStr = doQuery(heads, params, body, method == null ? HttpMethod.POST : method, thirdServiceUrl);
        //解析数据
        Pair<List<JSONObject>, PageBO> pair = analysisResult(queryStr, thirdType);
        List<JSONObject> resultList = pair.getLeft();
        if (CollectionUtils.isEmpty(resultList)) {
            return null;
        }
        //保存es，数据库（可以做成异步）
        saveData(resultList, thirdType);
        //设置分页属性
        Object[] objects = resultList.toArray();
        PageInfo<Object> doPageInfo = new PageInfo<>(Lists.newArrayList(objects));
        PageBO pageBO = pair.getRight();
        if (!Objects.isNull(pageBO)) {
            doPageInfo.setTotal(pageBO.getTotal());
            doPageInfo.setPages(pageBO.getPages());
            doPageInfo.setHasNextPage(pageBO.getHasNextPage());
            doPageInfo.setPageNum(pageBO.getPageNum());
            doPageInfo.setPageSize(pageBO.getPageSize());
        }
        return doPageInfo;
    }

    /**
     * 可能这边需要增加分页的请求参数，如有需求覆盖重写
     *
     * @param params
     * @param pageNo
     * @param pageSize
     */
    protected void addPageInfoToParams(Map<String, Object> params, int pageNo, int pageSize) {

    }

    /**
     * 校验参数
     *
     * @param params
     * @param thirdType
     */
    private void checkParams(Map<String, Object> params, ThirdType thirdType) {
        if (Objects.isNull(params) || Objects.isNull(thirdType)) {
            throw new IllegalArgumentException();
        }
        checkThirdType(thirdType);
    }

    /**
     * 先从数据库查询
     *
     * @param params
     * @param thirdType
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    private List<ThirdDataDO> queryDataSource(Map<String, Object> params, ThirdType thirdType, int pageNo, int pageSize)
            throws Exception {
        //todo 参数要转化下
        ThirdDataDO thirdDataDO = mapToBean(params, thirdType);
        //todo 这里后续可以记录下查到的该数据有多少，可以直接跳过数据库层面的查找
        PageHelper.startPage(pageNo, pageSize);
        List<ThirdDataDO> list = thirdDataMapper.select(thirdDataDO);
        return list;
    }

    /**
     * 校验类型
     * @param thirdType
     */
    protected abstract void checkThirdType(ThirdType thirdType);

    /**
     * 保存数据库
     *
     * @param objectList
     * @param thirdType
     */
    protected void saveDataSource(List<JSONObject> objectList, ThirdType thirdType) {
        if (!CollectionUtils.isEmpty(objectList)) {
            IAnalysis analysis = AnalysisFactoryUtil.getAnalysisResult(thirdType);
            Map<String, Map<String, Object>> mapMap = analysis.esData(objectList);
            List<ThirdDataDO> doList = new ArrayList<>(objectList.size());
            ThirdDataDO thirdDataDO;
            if (!CollectionUtils.isEmpty(mapMap)) {
                for (Map.Entry<String, Map<String, Object>> entry : mapMap.entrySet()) {
                    thirdDataDO = new ThirdDataDO();
                    thirdDataDO.setNo(entry.getKey());
                    thirdDataDO.setValue(JSON.toJSONString(entry.getValue()));
                    thirdDataDO.setThirdType(String.valueOf(thirdType.getCode()));
                    thirdDataDO.setDataType(thirdType.getCode()/1000%10);
                    doList.add(thirdDataDO);
                }
            }
            thirdDataMapper.replaceList(doList);
        }
    }

    /**
     * 获取其请求url
     *
     * @param thirdType
     * @return
     */
    protected abstract String setThirdServiceUrl(ThirdType thirdType);

    /**
     * map转化
     *
     * @param params
     * @param thirdType
     * @return
     * @throws Exception
     */
    protected ThirdDataDO mapToBean(Map<String, Object> params, ThirdType thirdType) throws Exception {
        ThirdDataDO thirdDataDO = CommonConverterUtil.MapToBean(params, ThirdDataDO.class);
        thirdDataDO.setThirdType(String.valueOf(thirdType.getCode()));
        thirdDataDO.setDataType(thirdType.getCode()/1000%10);
        //todo 可能有特殊情况需要处理，可以重写该方法
        return thirdDataDO;
    }

    /**
     * 具体查询逻辑
     *
     * @param heads
     * @param params
     * @param method
     * @param thirdServiceUrl
     * @return
     * @throws Exception
     */
    protected String doQuery(Map<String, String> heads, Map<String, Object> params, Map<String, Object> body,
                             HttpMethod method, String thirdServiceUrl) throws Exception {
        heads = setRequestHeads(heads);
        params = setRequestParams(params);
        body = setRequestBody(body);
        MultiValueMap requestHeaders;
        URI uri = new URI(thirdServiceUrl);
        if (heads instanceof MultiValueMap) {
            requestHeaders = (MultiValueMap) heads;
        } else {
            requestHeaders = new HttpHeaders();
            requestHeaders.setAll(heads);
        }
        if (!CollectionUtils.isEmpty(params)) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(thirdServiceUrl);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (StringUtils.isNotBlank(entry.getKey()) && !Objects.isNull(entry.getValue())) {
                    builder.queryParam(entry.getKey(), entry.getValue().toString());
                }
            }
            uri = builder.build().toUri();
        }
        HttpEntity<String> requestEntity = new HttpEntity<>(CollectionUtils.isEmpty(body) ?
                null : JSON.toJSONString(body), requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(uri, method, requestEntity, String.class);
        String result = null;
        if (HttpStatus.OK == response.getStatusCode()) {
            result = response.getBody();
            log.info("请求第三方接口返回结果:{}", result);
        }
        return result;
    }

    /**
     * 设置请求head部信息，需要的话子类覆盖
     *
     * @param heads
     */
    protected Map<String, String> setRequestHeads(Map<String, String> heads) throws Exception {
        return heads;
    }

    /**
     * 设置请求params部信息，需要的话子类覆盖
     *
     * @param params
     */
    protected Map<String, Object> setRequestParams(Map<String, Object> params) {
        return params;
    }

    /**
     * 设置请求body部信息，需要的话子类覆盖
     *
     * @param body
     */
    protected Map<String, Object> setRequestBody(Map<String, Object> body) {
        return body;
    }

    /**
     * 解析结果
     *
     * @param queryStr
     * @param thirdType
     * @return
     */
    protected Pair<List<JSONObject>, PageBO> analysisResult(String queryStr, ThirdType thirdType) {
        IAnalysis analysis = AnalysisFactoryUtil.getAnalysisResult(thirdType);
        return analysis.analysis(queryStr);
    }

    /**
     * 将数据保存到es中
     *
     * @param resultList
     */
    protected void saveEsData(List<JSONObject> resultList, ThirdType thirdType) {
        if (StringUtils.isNotBlank(thirdType.getEsIndex())) {
            IAnalysis analysis = AnalysisFactoryUtil.getAnalysisResult(thirdType);
            Map<String, Map<String, Object>> mapMap = analysis.esData(resultList);
            if (CollectionUtils.isEmpty(mapMap)) {
                return;
            }
            ElasticSearchConfig.saveEsDataWithBulk(mapMap, thirdType.getEsIndex());
        }
    }

    /**
     * 保存数据
     *
     * @param resultList
     * @param thirdType
     */
    private void saveData(List<JSONObject> resultList, ThirdType thirdType) {
        AsynThreadPoolConfig.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    saveEsData(resultList, thirdType);
                } catch (Exception e) {
                    log.error("save third data to es error", e);
                }

            }
        });
        AsynThreadPoolConfig.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    saveDataSource(resultList, thirdType);
                } catch (Exception e) {
                    log.error("save third data to datasource error", e);
                }
            }
        });
    }
}
