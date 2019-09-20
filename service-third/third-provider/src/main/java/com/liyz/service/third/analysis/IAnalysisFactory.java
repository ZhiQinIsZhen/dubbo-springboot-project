package com.liyz.service.third.analysis;

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
}
