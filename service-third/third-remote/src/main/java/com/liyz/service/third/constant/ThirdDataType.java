package com.liyz.service.third.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/15 16:41
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ThirdDataType {

    /**
     * 法海数据类别
     */
    @AllArgsConstructor
    public enum FhDataType {
        CPWS("cpws", "裁判文书"),
        ZXGG("zxgg", "执行公告"),
        SHIXIN("shixin", "失信公告"),
        WDHMD("wdhmd", "逾期催收名单"),
        BGT("bgt", "曝光台"),
        KTGG("ktgg", "开庭公告"),
        FYGG("fygg", "法院公告"),
        AJLC("ajlc", "案件流程"),
        ;

        @Getter
        private String code;

        @Getter
        private String desc;

        public static FhDataType getByCode(String code) {
            for (FhDataType fhDataType : FhDataType.values()) {
                if (fhDataType.code.equals(code)) {
                    return fhDataType;
                }
            }
            return null;
        }
    }

    /**
     * 鹏元数据类别
     */
    @AllArgsConstructor
    public enum PyDataType {
        DESC("desc", "personAntiSpoofingDescInfo", "个人反欺诈描述"),
        SCORE("score", "personAntiSpoofingInfo", "机构评分"),
        RISK("risk", "fraudRiskInfo", "风险名单"),
        ECON("econ", "econnoisserurInfo", "羊毛党"),
        CREDIT("credit", "creditBehaviorInfo", "借贷"),
        LOAN("loan", "overdueLoanInfo", "信贷逾期"),
        RISK_INFO("risk_info", "personRiskInfo", "个人风险信息"),
        QUERY("query", "historySimpleQueryInfo", "查询历史"),
        ;

        @Getter
        private String code;
        @Getter
        private String attribute;
        @Getter
        private String desc;

        public static PyDataType getByCode(String code) {
            for (PyDataType pyDataType : PyDataType.values()) {
                if (pyDataType.code.equals(code)) {
                    return pyDataType;
                }
            }
            return null;
        }
    }

    /**
     * 鹏元查询类型
     */
    @AllArgsConstructor
    public enum PySearchType {
        BLANK("25136", "主机对主机_全国个人信息查询（互联网客户、专网银行客户适用）"),
        NO_BLANK("33107", "主机对主机_全国个人信息查询（专网非银行客户适用）"),
        ;

        @Getter
        private String code;

        @Getter
        private String desc;
    }

    /**
     * 百融策略类型
     */
    @AllArgsConstructor
    public enum BrStrategyType {
        STR0024596("STR0024596", "反欺诈-个人资质")
        ;

        @Getter
        private String code;

        @Getter
        private String desc;
    }
}
