package com.liyz.service.third.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/18 11:23
 */
@AllArgsConstructor
public enum ThirdType {

    /**
     * 十进制高四位代表第三方类别，第五位代表企业还是个人，后三位是接口subUrl类别
     * 比如：10011005 -> 1001：企查查；1：企业；005：企业模糊
     */
    QCC_QYMH(10011005, "/ECIV4/Search", "enterprise_data", "企查查企业模糊"),
    QCC_QYJZ(10011010, "/ECIV4/GetFullDetailsByName", null, "企查查企业精准"),
    QCC_DWTZ(10011015, "/ECIInvestment/GetInvestmentList", null, "企查查企业对外投资列表"),

    FH_GRXQ(10020005, "/fhfk/person", null, "法海个人详情"),
    FH_QYXQ(10021005, "/fhfk/query", null, "法海企业详情"),

    PY_GRFQZ(10030005, "/services/WebServiceSingleQuery?wsdl", null, "鹏元个人反欺诈"),

    BR_GRFQZ(10040005, "", null, "百融个人反欺诈"),

    TD_GRFQZ(10050005, "/bodyguard/apply/v4.5", null, "同盾个人反欺诈"),
    ;

    @Getter
    private int code;
    @Getter
    private String subUrl;
    @Getter
    private String esIndex;
    @Getter
    private String desc;

    public static ThirdType getByCode(int code) {
        for (ThirdType thirdType : ThirdType.values()) {
            if (thirdType.code == code) {
                return thirdType;
            }
        }
        return null;
    }
}
