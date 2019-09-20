package com.liyz.service.third.analysis;

import com.liyz.service.third.constant.ThirdType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/19 13:53
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalysisFactoryUtil {

    public static IAnalysis getAnalysisResult(ThirdType thirdType) {
        IAnalysis analysis;
        AnalysisFactory analysisFactory = AnalysisFactory.getInstance();
        switch (thirdType) {
            case QCC_QYMH:
                analysis = analysisFactory.getQccQyMh();
                break;
            case QCC_QYJZ:
                analysis = analysisFactory.getQccQyJz();
                break;
            default:
                throw new IllegalArgumentException("no this thirdType:" + thirdType.getCode() + "analysis");
        }
        return analysis;
    }
}
