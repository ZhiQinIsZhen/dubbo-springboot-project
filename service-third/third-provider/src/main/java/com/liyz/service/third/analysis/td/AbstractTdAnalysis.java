package com.liyz.service.third.analysis.td;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liyz.service.third.analysis.IAnalysis;
import com.liyz.service.third.analysis.bo.PageBO;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/17 15:30
 */
public abstract class AbstractTdAnalysis implements IAnalysis {

    @Override
    public Pair<List<JSONObject>, PageBO> analysis(String value) {
        boolean success = JSON.parseObject(value).getBooleanValue("success");
        Pair<List<JSONObject>, PageBO> pair = null;
        if (success) {
            pair = doAnalysis(value);
        }
        return pair;
    }

    protected abstract Pair<List<JSONObject>, PageBO> doAnalysis(String value);

    @Override
    public Map<String, Pair<Map<String, Object>, JSONObject>> esData(List<JSONObject> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return doEsData(list);
    }

    protected abstract Map<String, Pair<Map<String, Object>, JSONObject>> doEsData(List<JSONObject> list);
}
