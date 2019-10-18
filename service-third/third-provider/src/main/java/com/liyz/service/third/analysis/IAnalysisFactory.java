package com.liyz.service.third.analysis;

import com.liyz.service.third.constant.ThirdDataType;

import java.util.List;

/**
 * 注释:解析工厂
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/19 11:26
 */
public interface IAnalysisFactory {

    /**
     * 企查查企业模糊
     *
     * @return
     */
    IAnalysis getQccQyMh();

    /**
     * 企查查企业精准
     *
     * @return
     */
    IAnalysis getQccQyJz();

    /**
     * 企查查对外投资
     *
     * @return
     */
    IAnalysis getQccDwtz();

    /**
     * 法海个人
     *
     * @return
     */
    IAnalysis getFhGrcx();

    /**
     * 法海企业
     *
     * @return
     */
    IAnalysis getFhQycx(ThirdDataType.FhDataType fhDataType);

    /**
     * 鹏元个人反欺诈
     *
     * @return
     */
    IAnalysis getPyGrfqz(List<ThirdDataType.PyDataType> pyDataTypes);

    /**
     * 百融个人反欺诈
     *
     * @return
     */
    IAnalysis getBrGrfqz();

    /**
     * 同盾个人反欺诈
     *
     * @return
     */
    IAnalysis getTdGrfqz();
}
