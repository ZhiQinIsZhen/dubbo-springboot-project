package com.liyz.service.third.analysis;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/16 9:40
 */
public interface IXmlToJson {

    default JSONObject xmlToJson(String xmlStr) throws JDOMException, IOException {
        if (StringUtils.isBlank(xmlStr)) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        InputStream is = new ByteArrayInputStream(xmlStr.getBytes("GBK"));
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(is);
        Element root = doc.getRootElement();
        jsonObject.put(root.getName(), doXmlToJson(root));
        return jsonObject;
    }

    default JSONObject doXmlToJson(Element element) {
        List<Element> node = element.getChildren();
        Element et;
        JSONObject obj = new JSONObject();
        List list;
        List<Attribute> attributes = element.getAttributes();
        for (Attribute attribute : attributes) {
            obj.put(attribute.getName(), attribute.getValue());
        }
        for (int i = 0; i < node.size(); i++) {
            list = new LinkedList();
            et = node.get(i);
            if ("".equals(et.getTextTrim())) {
                if (et.getChildren().size() == 0)
                    continue;
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(doXmlToJson(et));
                obj.put(et.getName(), list);
            } else {
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(et.getTextTrim());
                obj.put(et.getName(), list);
            }
        }
        return obj;
    }

}
