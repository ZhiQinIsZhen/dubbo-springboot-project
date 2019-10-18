package com.liyz.service.third.analysis.br;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.liyz.service.third.analysis.bo.PageBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/17 14:30
 */
@Slf4j
public class BrGrfqzAnalysis extends AbstractBrAnalysis {

    @Override
    protected Pair<List<JSONObject>, PageBO> doAnalysis(String value) {
        JSONObject valueObject = JSON.parseObject(value);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("swift_number", valueObject.get("swift_number"));
        jsonObject.put("Flag", valueObject.getJSONObject("Flag"));
        //注：FraudRelation_g.List_level >= 8，则建议是高的 - 团伙欺诈
        jsonObject.put("FraudRelation_g", valueObject.getJSONObject("FraudRelation_g"));
        //注：Rule.Result.Final_weight >= 80，建议拒绝 - 机构评分
        jsonObject.put("Rule", valueObject.getJSONObject("Rule"));
        return Pair.of(Lists.newArrayList(jsonObject), null);
    }

    @Override
    protected Map<String, Pair<Map<String, Object>, JSONObject>> doEsData(List<JSONObject> list) {
        Map<String, Pair<Map<String, Object>, JSONObject>> esDatas = new HashMap<>();
        for (JSONObject object : list) {
            //百融swift_number
            String entryId = object.getString("swift_number");
            esDatas.put(entryId, Pair.of(null, object));
        }
        return esDatas;
    }
}
