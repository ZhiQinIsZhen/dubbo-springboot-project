package com.liyz.service.third.handler;

import com.alibaba.fastjson.JSONObject;
import com.liyz.service.third.handler.abs.AbstractThirdService;
import com.liyz.service.third.analysis.AnalysisFactoryUtil;
import com.liyz.service.third.analysis.IAnalysis;
import com.liyz.service.third.analysis.bo.PageBO;
import com.liyz.service.third.config.ElasticSearchConfig;
import com.liyz.service.third.constant.ThirdConstant;
import com.liyz.service.third.constant.ThirdType;
import com.liyz.service.third.model.ThirdDataDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注释:法海handler
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/15 13:49
 */
@Slf4j
public class FhThirdServiceHandler extends AbstractThirdService {

    private String dataType;

    public FhThirdServiceHandler() {
        setLoadDataSource(false);
    }

    public FhThirdServiceHandler(boolean saveEs) {
        this(saveEs, null);
    }

    public FhThirdServiceHandler(boolean saveEs, String dataType) {
        this();
        setSaveEs(saveEs);
        this.dataType = dataType;
    }

    @Override
    protected Pair<List<JSONObject>, PageBO> analysisResult(String queryStr, ThirdType thirdType) {
        IAnalysis analysis = AnalysisFactoryUtil.getAnalysisResult(thirdType, dataType);
        return analysis.analysis(queryStr);
    }

    @Override
    protected void addPageInfoToParams(Map<String, Object> params, int pageNo, int pageSize) {
        params.put("pageno", String.valueOf(getPageNoSize().get().getLeft()));
        params.put("range", String.valueOf(getPageNoSize().get().getRight()));
        super.addPageInfoToParams(params, pageNo, pageSize);
    }

    @Override
    protected Map<String, Object> setRequestParams(Map<String, Object> params) {
        params.put("authCode", getThirdServiceUrlConfig().getFhAuthCode());
        return super.setRequestParams(params);
    }

    @Override
    protected void checkThirdType(ThirdType thirdType) {
        if (ThirdConstant.HF_CODE.intValue() != thirdType.getCode()/10000) {
            throw new IllegalArgumentException("thirdType is not fh service type!");
        }
    }

    @Override
    protected String setThirdServiceUrl(ThirdType thirdType) {
        return getThirdServiceUrlConfig().getFhUrl() + thirdType.getSubUrl();
    }

    @Override
    protected void saveEsData(List<JSONObject> resultList, ThirdType thirdType) {
        //由于不存储到es
        if (true) {
            return;
        }
        if (StringUtils.isNotBlank(thirdType.getEsIndex())) {
            IAnalysis analysis = AnalysisFactoryUtil.getAnalysisResult(thirdType, dataType);
            Map<String, Pair<Map<String, Object>, JSONObject>> mapMap = analysis.esData(resultList);
            if (CollectionUtils.isEmpty(mapMap)) {
                return;
            }
            Map<String,Map<String, Object>> esBulkData = new HashMap<>(mapMap.size());
            for (Map.Entry<String, Pair<Map<String, Object>, JSONObject>> entry : mapMap.entrySet()) {
                esBulkData.put(entry.getKey(), entry.getValue().getLeft());
            }
            ElasticSearchConfig.saveEsDataWithBulk(esBulkData, thirdType.getEsIndex());
        }
    }

    @Override
    protected void saveDataSource(List<JSONObject> objectList, ThirdType thirdType) {
        if (!CollectionUtils.isEmpty(objectList)) {
            IAnalysis analysis = AnalysisFactoryUtil.getAnalysisResult(thirdType, dataType);
            Map<String, Pair<Map<String, Object>, JSONObject>> mapMap = analysis.esData(objectList);
            List<ThirdDataDO> doList = new ArrayList<>(objectList.size());
            ThirdDataDO thirdDataDO;
            if (!CollectionUtils.isEmpty(mapMap)) {
                for (Map.Entry<String, Pair<Map<String, Object>, JSONObject>> entry : mapMap.entrySet()) {
                    //todo 其实这里最好再加一个dataType类型
                    thirdDataDO = new ThirdDataDO();
                    thirdDataDO.setNo(entry.getKey());
                    thirdDataDO.setValue(entry.getValue().getRight().toJSONString());
                    thirdDataDO.setThirdType(String.valueOf(thirdType.getCode()));
                    thirdDataDO.setDataType(thirdType.getCode()/1000%10);
                    doList.add(thirdDataDO);
                }
            }
            replaceMapper(doList);
        }
    }
}
