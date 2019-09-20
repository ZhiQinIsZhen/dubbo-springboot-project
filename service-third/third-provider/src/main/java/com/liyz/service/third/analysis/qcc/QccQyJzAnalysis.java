package com.liyz.service.third.analysis.qcc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liyz.service.third.bo.PageBO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
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
    protected Map<String, Map<String, Object>> doEsData(List<JSONObject> list) {
        //todo 暂时没有这个需求，不想弄
        return null;
    }
}
