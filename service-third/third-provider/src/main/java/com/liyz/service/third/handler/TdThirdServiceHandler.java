package com.liyz.service.third.handler;

import com.liyz.service.third.constant.ThirdConstant;
import com.liyz.service.third.constant.ThirdType;
import com.liyz.service.third.handler.abs.AbstractThirdService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/17 15:06
 */
@Slf4j
public class TdThirdServiceHandler  extends AbstractThirdService {

    public TdThirdServiceHandler() {
        setLoadDataSource(false);
    }

    public TdThirdServiceHandler(boolean saveEs) {
        this();
        setSaveEs(saveEs);
        setReAnalysis(true);
    }

    @Override
    protected void checkThirdType(ThirdType thirdType) {
        if (ThirdConstant.TD_CODE.intValue() != thirdType.getCode()/10000) {
            throw new IllegalArgumentException("thirdType is not td service type!");
        }
    }

    @Override
    protected String setThirdServiceUrl(ThirdType thirdType) {
        return getThirdServiceUrlConfig().getTdUrl();
    }

    @Override
    protected Map<String, String> setRequestHeads(Map<String, String> heads) throws Exception {
        if (heads == null) {
            heads = new HashMap<>();
        }
        heads.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        return super.setRequestHeads(heads);
    }

    @Override
    protected Map<String, Object> setRequestParams(Map<String, Object> params) {
        params.put("partner_code", getThirdServiceUrlConfig().getTdCode());
        params.put("partner_key", getThirdServiceUrlConfig().getTdKey());
        params.put("app_name", getThirdServiceUrlConfig().getTdAppName());
        return super.setRequestParams(params);
    }
}
