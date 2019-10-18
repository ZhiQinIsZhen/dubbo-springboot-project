package com.liyz.service.third.handler;

import com.alibaba.fastjson.JSON;
import com.liyz.service.third.analysis.bo.BrBaseBO;
import com.liyz.service.third.analysis.bo.BrQueryBO;
import com.liyz.service.third.constant.ThirdConstant;
import com.liyz.service.third.constant.ThirdDataType;
import com.liyz.service.third.constant.ThirdType;
import com.liyz.service.third.handler.abs.AbstractThirdService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * 注释:百融handler
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/17 11:25
 */
@Slf4j
public class BrThirdServiceHandler extends AbstractThirdService {

    public BrThirdServiceHandler() {
        setLoadDataSource(false);
    }

    public BrThirdServiceHandler(boolean saveEs) {
        this();
        setSaveEs(saveEs);
        setReAnalysis(true);
    }

    @Override
    protected void checkThirdType(ThirdType thirdType) {
        if (ThirdConstant.BR_CODE.intValue() != thirdType.getCode()/10000) {
            throw new IllegalArgumentException("thirdType is not br service type!");
        }
    }

    @Override
    protected String setThirdServiceUrl(ThirdType thirdType) {
        return null;
    }

    @Override
    protected String doQuery(Map<String, String> heads, Map<String, Object> params, Map<String, Object> body, HttpMethod method, String thirdServiceUrl) throws Exception {
        BrQueryBO<BrBaseBO> brQueryBO = getRequestBo(params);
        String jsonData = JSON.toJSONString(brQueryBO);
        String res = getThirdServiceUrlConfig().getMs().getApiData(jsonData, getThirdServiceUrlConfig().getBrApiCode());
        if(StringUtils.isNotBlank(res)){
            JSONObject json = JSONObject.fromObject(res);
            if(json.containsKey("code") && (ThirdConstant.BR_LOGIN_TIME_OUT).equals(json.getString("code"))){
                JSONObject jsonD = JSONObject.fromObject(jsonData);
                jsonD.put("tokenid", getThirdServiceUrlConfig().getBrTokenId());
                res = getThirdServiceUrlConfig().getMs().getApiData(jsonD.toString(), getThirdServiceUrlConfig().getBrApiCode());
                return res;
            }
        }
        log.info("br third service result :{}", res);
        return res;

    }

    protected BrQueryBO<BrBaseBO> getRequestBo(Map<String, Object> params) {
        BrBaseBO brBaseBO = BrBaseBO.builder()
                .id(String.valueOf(params.get("id")))
                .name(String.valueOf(params.get("name")))
                .cell(String.valueOf(params.get("cell")))
                .strategy_id(ThirdDataType.BrStrategyType.STR0024596.getCode())
                .build();
        BrQueryBO<BrBaseBO> brQueryBO = BrQueryBO.builder()
                .reqData(brBaseBO)
                .apiName(getThirdServiceUrlConfig().getBrApiName())
                .tokenid(getThirdServiceUrlConfig().getBrTokenId())
                .build();
        return brQueryBO;
    }

}
