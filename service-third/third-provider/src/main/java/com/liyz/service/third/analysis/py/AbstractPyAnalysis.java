package com.liyz.service.third.analysis.py;

import com.alibaba.fastjson.JSONObject;
import com.liyz.service.third.analysis.IAnalysis;
import com.liyz.service.third.analysis.IXmlToJson;
import com.liyz.service.third.analysis.bo.PageBO;
import com.liyz.service.third.constant.ThirdConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/15 19:25
 */
@Slf4j
public abstract class AbstractPyAnalysis implements IAnalysis, IXmlToJson {

    @Override
    public Pair<List<JSONObject>, PageBO> analysis(String value) {
        JSONObject jsonObject;
        try {
            jsonObject = xmlToJson(value);
        } catch (Exception e) {
            log.error("xml parse json error", e);
            jsonObject = null;
        }
        if (jsonObject == null) {
            return null;
        }
        Pair<List<JSONObject>, PageBO> pair = null;
        JSONObject report = jsonObject.getJSONObject("cisReports").getJSONArray("cisReport").getJSONObject(0);
        if (report!= null && ThirdConstant.PY_QUERY_SUCCESS.equals(report.getString("treatResult"))) {
            pair = doAnalysis(jsonObject);
        }
        return pair;
    }

    protected abstract Pair<List<JSONObject>, PageBO> doAnalysis(JSONObject jsonObject);

    @Override
    public Map<String, Pair<Map<String, Object>, JSONObject>> esData(List<JSONObject> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return doEsData(list);
    }

    protected abstract Map<String, Pair<Map<String, Object>, JSONObject>> doEsData(List<JSONObject> list);
}
