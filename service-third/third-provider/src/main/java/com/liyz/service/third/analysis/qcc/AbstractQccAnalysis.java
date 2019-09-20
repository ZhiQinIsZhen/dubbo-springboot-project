package com.liyz.service.third.analysis.qcc;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liyz.service.third.analysis.IAnalysis;
import com.liyz.service.third.bo.PageBO;
import com.liyz.service.third.constant.ThirdConstant;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/19 14:13
 */
public abstract class AbstractQccAnalysis implements IAnalysis {

    @Override
    public Pair<List<JSONObject>, PageBO> analysis(String value) {
        String code = JSON.parseObject(value).getString("Status");
        Pair<List<JSONObject>, PageBO> pair = null;
        if (ThirdConstant.QCC_QUERY_SUCCESS.equals(code)) {
            pair = doAnalysis(value);
        }
        return pair;
    }

    protected abstract Pair<List<JSONObject>, PageBO> doAnalysis(String value);

    @Override
    public Map<String, Map<String, Object>> esData(List<JSONObject> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return doEsData(list);
    }

    protected abstract Map<String, Map<String, Object>> doEsData(List<JSONObject> list);
}
