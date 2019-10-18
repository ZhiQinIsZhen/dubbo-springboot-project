package com.liyz.service.third.handler;

import com.alibaba.fastjson.JSONObject;
import com.liyz.service.third.analysis.AnalysisFactoryUtil;
import com.liyz.service.third.analysis.IAnalysis;
import com.liyz.service.third.analysis.bo.PageBO;
import com.liyz.service.third.analysis.bo.PyConditionBO;
import com.liyz.service.third.analysis.bo.PyConditionsBO;
import com.liyz.service.third.analysis.bo.PyItemBO;
import com.liyz.service.third.constant.ThirdConstant;
import com.liyz.service.third.constant.ThirdDataType;
import com.liyz.service.third.constant.ThirdType;
import com.liyz.service.third.handler.abs.AbstractThirdService;
import com.liyz.service.third.util.CompressStringUtil;
import com.liyz.service.third.util.XStreamUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.codehaus.xfire.client.Client;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.http.HttpMethod;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 注释:鹏元handler
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/16 14:28
 */
@Slf4j
public class PyThirdServiceHandler extends AbstractThirdService {

    private String dataType;

    public PyThirdServiceHandler() {
        setLoadDataSource(false);
    }

    public PyThirdServiceHandler(boolean saveEs) {
        this(saveEs, null);
    }

    public PyThirdServiceHandler(boolean saveEs, String dataType) {
        this();
        setSaveEs(saveEs);
        setReAnalysis(StringUtils.isNotBlank(dataType));
        this.dataType = dataType;
    }

    @Override
    protected Pair<List<JSONObject>, PageBO> analysisResult(String queryStr, ThirdType thirdType) {
        IAnalysis analysis = AnalysisFactoryUtil.getAnalysisResult(thirdType, dataType);
        return analysis.analysis(queryStr);
    }

    @Override
    protected void checkThirdType(ThirdType thirdType) {
        if (ThirdConstant.PY_CODE.intValue() != thirdType.getCode()/10000) {
            throw new IllegalArgumentException("thirdType is not py service type!");
        }
    }

    @Override
    protected String setThirdServiceUrl(ThirdType thirdType) {
        return getThirdServiceUrlConfig().getPyUrl() + thirdType.getSubUrl();
    }

    @Override
    protected String doQuery(Map<String, String> heads, Map<String, Object> params, Map<String, Object> body, HttpMethod method, String thirdServiceUrl) throws Exception {
        PyConditionsBO pyConditionsBO = getRequestBo(params);
        PyItemBO reasonIDItem = new PyItemBO();
        reasonIDItem.setName("queryReasonID");
        reasonIDItem.setValue("101");
        pyConditionsBO.getCondition().getItem().add(reasonIDItem);
        PyItemBO refIDItem = new PyItemBO();
        refIDItem.setName("refID");
        refIDItem.setValue("");
        pyConditionsBO.getCondition().getItem().add(refIDItem);
        String str = XStreamUtil.beanToXmlWithTag(pyConditionsBO, XStreamUtil.GBK_ENCODE, Boolean.FALSE);
        Client client = new Client(new URL(thirdServiceUrl));
        Object [] results = client.invoke("queryReport", new Object[]{getThirdServiceUrlConfig().getPyAccount(),
                getThirdServiceUrlConfig().getPySecret(), str, "xml"});
        String pyStr = null;
        if (Objects.nonNull(results) && results.length > 0) {
            if (results[0] instanceof String) {
                pyStr = results[0].toString();
            } else if (results[0] instanceof org.w3c.dom.Document) {
                org.w3c.dom.Document document = (org.w3c.dom.Document) results[0];
                org.w3c.dom.Element element = document.getDocumentElement();
                NodeList children = element.getChildNodes();
                Node node = children.item(0);
                pyStr = node.getNodeValue();
            }
        }
        if (StringUtils.isNotBlank(pyStr)) {
            Document document = DocumentHelper.parseText(pyStr);
            Element rootElement= document.getRootElement();
            if ("result".equalsIgnoreCase(rootElement.getName())) {
                Integer status =  Integer.valueOf(rootElement.elementTextTrim("status"));
                //失败
                if (status == 2) {
                    String errorCode = rootElement.elementTextTrim("errorCode");
                    String errorMsg = rootElement.elementTextTrim("errorMessage");
                    log.error("鹏元接口调用失败： {}", String.format("fail:鹏元,调用失败：status:%s,errorCode:%s,errorMsg:%s", status, errorCode, errorMsg));
                    return null;
                }
                //成功
                String returnValue = rootElement.elementTextTrim("returnValue");
                String xmlStr = CompressStringUtil.decompress(Base64.decodeBase64(returnValue));
                log.info("py third service result :{}", xmlStr);
                return xmlStr;
            }
        }
        return null;
    }

    private PyConditionsBO getRequestBo(Map<String, Object> params) {
        List<PyItemBO> list = new ArrayList<>();
        PyItemBO nameItem = new PyItemBO();
        nameItem.setName("name");
        nameItem.setValue(StringUtils.isBlank(String.valueOf(params.get("name"))) ? "" : String.valueOf(params.get("name")));
        list.add(nameItem);
        PyItemBO noItem = new PyItemBO();
        noItem.setName("documentNo");
        noItem.setValue(StringUtils.isBlank(String.valueOf(params.get("documentNo"))) ? "" : String.valueOf(params.get("documentNo")));
        list.add(noItem);
        PyItemBO phoneItem = new PyItemBO();
        phoneItem.setName("phone");
        phoneItem.setValue(StringUtils.isBlank(String.valueOf(params.get("phone"))) ? "" : String.valueOf(params.get("phone")));
        list.add(phoneItem);
        PyItemBO subreportIDsItem = new PyItemBO();
        subreportIDsItem.setName("subreportIDs");
        subreportIDsItem.setValue("96043");
        list.add(subreportIDsItem);
        PyConditionBO pyConditionBO = new PyConditionBO();
        pyConditionBO.setItem(list);
        pyConditionBO.setQueryType(ThirdDataType.PySearchType.BLANK.getCode());
        PyConditionsBO pyConditionsBO = new PyConditionsBO();
        pyConditionsBO.setCondition(pyConditionBO);
        return pyConditionsBO;
    }

    @Override
    protected void saveEsData(List<JSONObject> resultList, ThirdType thirdType) {
        //由于不存储到es
        if (true) {
            return;
        }
    }
}
