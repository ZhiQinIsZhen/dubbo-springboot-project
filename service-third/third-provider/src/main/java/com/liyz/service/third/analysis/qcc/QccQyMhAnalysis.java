package com.liyz.service.third.analysis.qcc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyz.service.third.analysis.bo.PageBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注释:企查企业查模糊解析器
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/19 12:13
 */
@Slf4j
public class QccQyMhAnalysis extends AbstractQccAnalysis {

    @Override
    protected Pair<List<JSONObject>, PageBO> doAnalysis(String value) {
        List<JSONObject> list = new ArrayList<>();
        JSONArray qccArr = JSON.parseObject(value).getJSONArray("Result");
        for (int i = 0; i < qccArr.size(); i++) {
            JSONObject object = (JSONObject) qccArr.get(i);
            list.add(object);
        }
        JSONObject paging = JSON.parseObject(value).getJSONObject("Paging");
        PageBO pageBO = null;
        if (paging != null) {
            Long total = paging.getLong("TotalRecords");
            Integer pageNum = paging.getInteger("PageIndex");
            Integer pageSize = paging.getInteger("PageSize");
            Integer pages = total.intValue()%pageSize == 0 ? total.intValue()/pageSize : total.intValue()/pageSize +1;
            pageBO = PageBO.builder()
                    .total(total)
                    .pages(pages)
                    .hasNextPage(pages > pageNum)
                    .pageNum(pageNum)
                    .pageSize(pageSize)
                    .build();
        }
        return Pair.of(list, pageBO);
    }

    @Override
    protected Map<String, Pair<Map<String, Object>, JSONObject>> doEsData(List<JSONObject> list) {
        Map<String, Pair<Map<String, Object>, JSONObject>> esDatas = new HashMap<>();
        for (JSONObject object : list) {
            Map<String, Object> esData = new HashMap<>();
            //公司名称
            esData.put("Name", object.getString("Name"));
            //法人名
            esData.put("OperName", object.getString("OperName"));
            //社会统一信用代码
            String creditCode = object.getString("CreditCode");
            if (StringUtils.isBlank(creditCode)) {
                creditCode = object.getString("No");
            }
            esData.put("CreditCode", creditCode);
            esData.put("updateTime", "");
            esDatas.put(creditCode, Pair.of(esData, object));
        }
        return esDatas;
    }
}
