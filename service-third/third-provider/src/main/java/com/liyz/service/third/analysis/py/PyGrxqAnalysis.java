package com.liyz.service.third.analysis.py;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.liyz.service.third.analysis.bo.PageBO;
import com.liyz.service.third.constant.ThirdDataType;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/16 10:22
 */
public class PyGrxqAnalysis extends AbstractPyAnalysis {

    private List<ThirdDataType.PyDataType> pyDataTypes;

    public PyGrxqAnalysis(List<ThirdDataType.PyDataType> pyDataTypes) {
        this.pyDataTypes = pyDataTypes;
    }

    @Override
    protected Pair<List<JSONObject>, PageBO> doAnalysis(JSONObject jsonObject) {
        JSONObject report = jsonObject.getJSONObject("cisReports").getJSONArray("cisReport").getJSONObject(0);
        if (CollectionUtils.isEmpty(pyDataTypes)) {
            return Pair.of(Lists.newArrayList(report), null);
        }
        JSONObject result = new JSONObject();
        result.put("reportID", report.get("reportID"));
        result.put("queryConditions", report.getJSONArray("queryConditions"));
        for (ThirdDataType.PyDataType pyDataType : pyDataTypes) {
            if (pyDataType != null) {
                JSONArray array = report.getJSONArray(pyDataType.getAttribute());
                result.put(pyDataType.getAttribute(), array);
            }
        }
        return Pair.of(Lists.newArrayList(result), null);
    }

    @Override
    protected Map<String, Pair<Map<String, Object>, JSONObject>> doEsData(List<JSONObject> list) {
        Map<String, Pair<Map<String, Object>, JSONObject>> esDatas = new HashMap<>();
        for (JSONObject object : list) {
            //鹏元batNo
            String entryId = object.getString("reportID");
            esDatas.put(entryId, Pair.of(null, object));
        }
        return esDatas;
    }

    public static void main(String[] args) {
        String xmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" +
                "<cisReports batNo=\"2019082016000024\" unitName=\"杭州仟金顶信息科技有限公司\" subOrgan=\"技术部\" queryUserID=\"qjdwsquery01\" queryCount=\"1\" receiveTime=\"20190820 16:14:53\">\n" +
                "<cisReport reportID=\"2019082016000024\" buildEndTime=\"2019-08-20 16:14:53\" queryReasonID=\"102\" subReportTypes=\"96043\" treatResult=\"1\" subReportTypesShortCaption=\"1、个人反欺诈分析报告(96043)\" hasSystemError=\"false\" isFrozen=\"false\">\n" +
                "<queryConditions>\n" +
                "<item>\n" +
                "<name>name</name>\n" +
                "<caption>被查询者姓名</caption>\n" +
                "<value>测试一</value>\n" +
                "</item>\n" +
                "<item>\n" +
                "<name>documentNo</name>\n" +
                "<caption>被查询者证件号码</caption>\n" +
                "<value>110000199001011112</value>\n" +
                "</item>\n" +
                "</queryConditions>\n" +
                "<personAntiSpoofingInfo subReportType=\"14241\" subReportTypeCost=\"96043\" treatResult=\"1\" errorMessage=\"\">\n" +
                "<riskScore>100</riskScore>\n" +
                "<riskLevel>高</riskLevel>\n" +
                "<suggest>建议拒绝</suggest>\n" +
                "<hitTypes>羊毛党名单，欺诈风险名单，高频信贷行为，信贷逾期名单，个人风险信息，被机构查询信息，多头申请记录</hitTypes>\n" +
                "</personAntiSpoofingInfo>\n" +
                "<personAntiSpoofingDescInfo subReportType=\"14242\" subReportTypeCost=\"96043\" treatResult=\"1\" errorMessage=\"\">\n" +
                "<personAntiSpoofingDesc>\n" +
                "1、反欺诈风险评分为100分，风险等级为高，建议拒绝。\n" +
                "        2、命中羊毛党名单。\n" +
                "        3、命中欺诈风险名单。\n" +
                "        4、检测到高频信贷行为。\n" +
                "        5、存在8笔逾期的信贷记录。\n" +
                "        6、存在18条个人风险概要信息。\n" +
                "        7、在近两年被机构查询过13次个人信息，疑似有9笔多头申请记录。\n" +
                "</personAntiSpoofingDesc>\n" +
                "</personAntiSpoofingDescInfo>\n" +
                "<fraudRiskInfo subReportType=\"14237\" subReportTypeCost=\"96043\" treatResult=\"1\" errorMessage=\"\">\n" +
                "<state>1</state>\n" +
                "</fraudRiskInfo>\n" +
                "<econnoisserurInfo subReportType=\"14236\" subReportTypeCost=\"96043\" treatResult=\"1\" errorMessage=\"\">\n" +
                "<state>1</state>\n" +
                "</econnoisserurInfo>\n" +
                "<creditBehaviorInfo subReportType=\"14238\" subReportTypeCost=\"96043\" treatResult=\"1\" errorMessage=\"\">\n" +
                "<last1MthsLoanCnt>19</last1MthsLoanCnt>\n" +
                "<last3MthsLoanCnt>20</last3MthsLoanCnt>\n" +
                "<last6MthsLoanCnt>21</last6MthsLoanCnt>\n" +
                "<last12MthsLoanCnt>13</last12MthsLoanCnt>\n" +
                "<loanOrgCnt>16</loanOrgCnt>\n" +
                "<avgCredits>35</avgCredits>\n" +
                "<loanOrderCnt>13</loanOrderCnt>\n" +
                "<loanClosedCnt>14</loanClosedCnt>\n" +
                "<loanNoClosedCnt>0</loanNoClosedCnt>\n" +
                "<undefinedCnt>0</undefinedCnt>\n" +
                "</creditBehaviorInfo>\n" +
                "<overdueLoanInfo subReportType=\"14239\" subReportTypeCost=\"96043\" treatResult=\"1\" errorMessage=\"\">\n" +
                "<overdueStat>\n" +
                "<overdueAmount>1102000</overdueAmount>\n" +
                "<overdueTotal>8</overdueTotal>\n" +
                "<overdueNoClosedCnt>7</overdueNoClosedCnt>\n" +
                "</overdueStat>\n" +
                "<overdueDetails>\n" +
                "<item>\n" +
                "<overdueAmount>100000</overdueAmount>\n" +
                "<overdueDays>逾期60-89天</overdueDays>\n" +
                "<settlement>未结清</settlement>\n" +
                "</item>\n" +
                "<item>\n" +
                "<overdueAmount>100000</overdueAmount>\n" +
                "<overdueDays>逾期60-89天</overdueDays>\n" +
                "<settlement>未结清</settlement>\n" +
                "</item>\n" +
                "<item>\n" +
                "<overdueAmount>400000</overdueAmount>\n" +
                "<overdueDays>逾期30-59天</overdueDays>\n" +
                "<settlement>未结清</settlement>\n" +
                "</item>\n" +
                "<item>\n" +
                "<overdueAmount>200000</overdueAmount>\n" +
                "<overdueDays>逾期30-59天</overdueDays>\n" +
                "<settlement>未结清</settlement>\n" +
                "</item>\n" +
                "<item>\n" +
                "<overdueAmount>100000</overdueAmount>\n" +
                "<overdueDays>逾期30-59天</overdueDays>\n" +
                "<settlement>未结清</settlement>\n" +
                "</item>\n" +
                "<item>\n" +
                "<overdueAmount>2000</overdueAmount>\n" +
                "<overdueDays>逾期30-59天</overdueDays>\n" +
                "<settlement>未知</settlement>\n" +
                "</item>\n" +
                "<item>\n" +
                "<overdueAmount>100000</overdueAmount>\n" +
                "<overdueDays>逾期7-29天</overdueDays>\n" +
                "<settlement>未结清</settlement>\n" +
                "</item>\n" +
                "<item>\n" +
                "<overdueAmount>100000</overdueAmount>\n" +
                "<overdueDays>逾期7-29天</overdueDays>\n" +
                "<settlement>未结清</settlement>\n" +
                "</item>\n" +
                "</overdueDetails>\n" +
                "</overdueLoanInfo>\n" +
                "<personRiskInfo subReportType=\"14227\" subReportTypeCost=\"96043\" treatResult=\"1\" errorMessage=\"\">\n" +
                "<stat>\n" +
                "<totleCount>18</totleCount>\n" +
                "<alCount>3</alCount>\n" +
                "<zxCount>3</zxCount>\n" +
                "<sxCount>3</sxCount>\n" +
                "<swCount>3</swCount>\n" +
                "<cqggCount>3</cqggCount>\n" +
                "<wdyqCount>3</wdyqCount>\n" +
                "</stat>\n" +
                "<summary>\n" +
                "<als>\n" +
                "<item>\n" +
                "<recordId>al_329576D41D18491481</recordId>\n" +
                "<bt>原告某与被告张三买卖合同纠纷一案</bt>\n" +
                "<ajlx>民事判决书</ajlx>\n" +
                "<sjnf>2009</sjnf>\n" +
                "<dsrlx>被告</dsrlx>\n" +
                "</item>\n" +
                "<item>\n" +
                "<recordId>al_329577D41D18491482</recordId>\n" +
                "<bt>原告许某良与被告张三买卖合同纠纷一案</bt>\n" +
                "<ajlx>民事判决书</ajlx>\n" +
                "<sjnf>2009</sjnf>\n" +
                "<dsrlx>被告</dsrlx>\n" +
                "</item>\n" +
                "<item>\n" +
                "<recordId>al_329578D41D18491483</recordId>\n" +
                "<bt>原告许良与被告张三买卖合同纠纷一案</bt>\n" +
                "<ajlx>民事判决书</ajlx>\n" +
                "<sjnf>2009</sjnf>\n" +
                "<dsrlx>被告</dsrlx>\n" +
                "</item>\n" +
                "</als>\n" +
                "<zxs>\n" +
                "<item>\n" +
                "<recordId>zx_4436174D43D0</recordId>\n" +
                "<bt>被执行人测试一一1</bt>\n" +
                "<zxbd>118200</zxbd>\n" +
                "<larq>2009-02-06</larq>\n" +
                "</item>\n" +
                "<item>\n" +
                "<recordId>zx_4436175D43D1</recordId>\n" +
                "<bt>被执行人测试一一2</bt>\n" +
                "<zxbd>118200</zxbd>\n" +
                "<larq>2009-02-06</larq>\n" +
                "</item>\n" +
                "<item>\n" +
                "<recordId>zx_4436176D43D2</recordId>\n" +
                "<bt>被执行人测试一一3</bt>\n" +
                "<zxbd>118200</zxbd>\n" +
                "<larq>2009-02-06</larq>\n" +
                "</item>\n" +
                "</zxs>\n" +
                "<sxs>\n" +
                "<item>\n" +
                "<recordId>sx_6877415D42D0</recordId>\n" +
                "<bt>测试一(失信被执行人)</bt>\n" +
                "<larq>2009-02-03</larq>\n" +
                "<fbrq>2014-12-17</fbrq>\n" +
                "</item>\n" +
                "<item>\n" +
                "<recordId>sx_6877416D42D1</recordId>\n" +
                "<bt>测试一(失信被执行人)2</bt>\n" +
                "<larq>2009-02-03</larq>\n" +
                "<fbrq>2014-12-17</fbrq>\n" +
                "</item>\n" +
                "<item>\n" +
                "<recordId>sx_6877417D42D2</recordId>\n" +
                "<bt>测试一(失信被执行人)3</bt>\n" +
                "<larq>2009-02-03</larq>\n" +
                "<fbrq>2014-12-17</fbrq>\n" +
                "</item>\n" +
                "</sxs>\n" +
                "<sws>\n" +
                "<item>\n" +
                "<recordId>sw_1403038D44D0</recordId>\n" +
                "<bt>测试一(非正常户)</bt>\n" +
                "<ggrq>2012-08-01</ggrq>\n" +
                "</item>\n" +
                "<item>\n" +
                "<recordId>sw_1403039D44D1</recordId>\n" +
                "<bt>测试一(非正常户)2</bt>\n" +
                "<ggrq>2012-08-01</ggrq>\n" +
                "</item>\n" +
                "<item>\n" +
                "<recordId>sw_1403037D44D2</recordId>\n" +
                "<bt>测试一(非正常户)3</bt>\n" +
                "<ggrq>2012-08-01</ggrq>\n" +
                "</item>\n" +
                "</sws>\n" +
                "<cqs>\n" +
                "<item>\n" +
                "<recordId>cq_10315D45D0</recordId>\n" +
                "<bt>被催欠人测试一</bt>\n" +
                "<fbrq>2014-01-23</fbrq>\n" +
                "</item>\n" +
                "<item>\n" +
                "<recordId>cq_10316D45D1</recordId>\n" +
                "<bt>被催欠人测试一2</bt>\n" +
                "<fbrq>2014-01-23</fbrq>\n" +
                "</item>\n" +
                "<item>\n" +
                "<recordId>cq_10317D45D2</recordId>\n" +
                "<bt>被催欠人测试一3</bt>\n" +
                "<fbrq>2014-01-23</fbrq>\n" +
                "</item>\n" +
                "</cqs>\n" +
                "<wdyqs>\n" +
                "<item>\n" +
                "<recordId>wdyq_4558264D46D0</recordId>\n" +
                "<bt>测试一</bt>\n" +
                "<fbrq>2013-07-16</fbrq>\n" +
                "</item>\n" +
                "<item>\n" +
                "<recordId>wdyq_4558265D46D1</recordId>\n" +
                "<bt>测试一2</bt>\n" +
                "<fbrq>2013-07-16</fbrq>\n" +
                "</item>\n" +
                "<item>\n" +
                "<recordId>wdyq_4558266D46D2</recordId>\n" +
                "<bt>测试一3</bt>\n" +
                "<fbrq>2013-07-16</fbrq>\n" +
                "</item>\n" +
                "</wdyqs>\n" +
                "</summary>\n" +
                "</personRiskInfo>\n" +
                "<historySimpleQueryInfo subReportType=\"19902\" subReportTypeCost=\"96043\" treatResult=\"1\" errorMessage=\"\">\n" +
                "<items>\n" +
                "<item>\n" +
                "<unitMember>互联网金融</unitMember>\n" +
                "<last1Month>0</last1Month>\n" +
                "<last3Month>0</last3Month>\n" +
                "<last6Month>3</last6Month>\n" +
                "<last12Month>4</last12Month>\n" +
                "<last18Month>4</last18Month>\n" +
                "<last24Month>4</last24Month>\n" +
                "</item>\n" +
                "<item>\n" +
                "<unitMember>小额贷款公司</unitMember>\n" +
                "<last1Month>0</last1Month>\n" +
                "<last3Month>0</last3Month>\n" +
                "<last6Month>0</last6Month>\n" +
                "<last12Month>5</last12Month>\n" +
                "<last18Month>5</last18Month>\n" +
                "<last24Month>5</last24Month>\n" +
                "</item>\n" +
                "<item>\n" +
                "<unitMember>消费金融公司</unitMember>\n" +
                "<last1Month>0</last1Month>\n" +
                "<last3Month>0</last3Month>\n" +
                "<last6Month>0</last6Month>\n" +
                "<last12Month>4</last12Month>\n" +
                "<last18Month>4</last18Month>\n" +
                "<last24Month>4</last24Month>\n" +
                "</item>\n" +
                "</items>\n" +
                "<count>\n" +
                "<last1Month>0</last1Month>\n" +
                "<last3Month>0</last3Month>\n" +
                "<last6Month>3</last6Month>\n" +
                "<last12Month>13</last12Month>\n" +
                "<last18Month>13</last18Month>\n" +
                "<last24Month>13</last24Month>\n" +
                "</count>\n" +
                "<suspectedBulllending>\n" +
                "<appplyCnt>9</appplyCnt>\n" +
                "<applyNetLoanCnt>5</applyNetLoanCnt>\n" +
                "<applyFinclCnt>4</applyFinclCnt>\n" +
                "</suspectedBulllending>\n" +
                "</historySimpleQueryInfo>\n" +
                "</cisReport>\n" +
                "</cisReports>";

        PyGrxqAnalysis pyGrxqAnalysis = new PyGrxqAnalysis(null);
        Pair<List<JSONObject>, PageBO> pair = pyGrxqAnalysis.analysis(xmlStr);

    }
}
