package com.liyz.service.third.analysis.td;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.liyz.service.third.analysis.bo.PageBO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/17 15:34
 */
public class TdGrfqzAnalysis extends AbstractTdAnalysis {

    @Override
    protected Pair<List<JSONObject>, PageBO> doAnalysis(String value) {
        JSONObject valueObject = JSON.parseObject(value);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", valueObject.get("id"));
        //注：result_desc.ANTIFRAUD.final_decision = "REJECT"，则建议拒绝
        jsonObject.put("result_desc", valueObject.getJSONObject("result_desc"));
        return Pair.of(Lists.newArrayList(jsonObject), null);
    }

    @Override
    protected Map<String, Pair<Map<String, Object>, JSONObject>> doEsData(List<JSONObject> list) {
        Map<String, Pair<Map<String, Object>, JSONObject>> esDatas = new HashMap<>();
        for (JSONObject object : list) {
            //同盾id
            String entryId = object.getString("id");
            esDatas.put(entryId, Pair.of(null, object));
        }
        return esDatas;
    }
}
