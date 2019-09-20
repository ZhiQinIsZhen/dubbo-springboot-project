package com.liyz.service.third.analysis;

import com.alibaba.fastjson.JSONObject;
import com.liyz.service.third.bo.PageBO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/19 12:00
 */
public interface IAnalysis {

    Pair<List<JSONObject>, PageBO> analysis(String value);

    Map<String, Map<String, Object>> esData(List<JSONObject> list);
}
