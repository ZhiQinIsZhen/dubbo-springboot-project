package com.liyz.service.third.analysis.qcc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liyz.service.third.analysis.bo.PageBO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注释:企查查企业精准解析器
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/19 14:11
 */
public class QccQyJzAnalysis extends AbstractQccAnalysis {

    @Override
    protected Pair<List<JSONObject>, PageBO> doAnalysis(String value) {
        List<JSONObject> list = new ArrayList<>();
        JSONObject object = JSON.parseObject(value).getJSONObject("Result");
        list.add(object);
        return Pair.of(list, null);
    }

    @Override
    protected Map<String, Pair<Map<String, Object>, JSONObject>> doEsData(List<JSONObject> list) {
        Map<String, Pair<Map<String, Object>, JSONObject>> esDatas = new HashMap<>();
        for (JSONObject object : list) {
            //社会统一信用代码
            String creditCode = object.getString("CreditCode");
            if (StringUtils.isBlank(creditCode)) {
                creditCode = object.getString("No");
            }
            esDatas.put(creditCode, Pair.of(null, object));
        }
        return esDatas;
    }
}
