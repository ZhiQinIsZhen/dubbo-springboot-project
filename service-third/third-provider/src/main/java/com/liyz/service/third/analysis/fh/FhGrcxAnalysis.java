package com.liyz.service.third.analysis.fh;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyz.service.third.analysis.bo.PageBO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/15 14:30
 */
public class FhGrcxAnalysis extends AbstractFhAnalysis {

    @Override
    protected Pair<List<JSONObject>, PageBO> doAnalysis(String value) {
        List<JSONObject> list = new ArrayList<>();
        JSONArray qccArr = JSON.parseObject(value).getJSONArray("entryList");
        for (int i = 0; i < qccArr.size(); i++) {
            JSONObject object = (JSONObject) qccArr.get(i);
            list.add(object);
        }
        Long total = JSON.parseObject(value).getLong("count");
        Integer pageNum = JSON.parseObject(value).getInteger("pageNo");
        Integer pageSize = JSON.parseObject(value).getInteger("range");
        Integer pages = total.intValue()%pageSize == 0 ? total.intValue()/pageSize : total.intValue()/pageSize +1;
        PageBO pageBO = PageBO.builder()
                .total(total)
                .pages(pages)
                .hasNextPage(pages > pageNum)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
        return Pair.of(list, pageBO);
    }

    @Override
    protected Map<String, Pair<Map<String, Object>, JSONObject>> doEsData(List<JSONObject> list) {
        Map<String, Pair<Map<String, Object>, JSONObject>> esDatas = new HashMap<>();
        for (JSONObject object : list) {
            //法海entryId
            String entryId = object.getString("entryId");
            esDatas.put(entryId, Pair.of(null, object));
        }
        return esDatas;
    }
}
