package com.liyz.service.third.analysis;

import com.liyz.service.third.analysis.qcc.QccQyJzAnalysis;
import com.liyz.service.third.analysis.qcc.QccQyMhAnalysis;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/19 12:02
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalysisFactory implements IAnalysisFactory {

    private static volatile AnalysisFactory singleton;

    public static final AnalysisFactory getInstance() {
        if (singleton == null) {
            synchronized (AnalysisFactory.class) {
                if (singleton == null) {
                    singleton = new AnalysisFactory();
                }
            }
        }
        return singleton;
    }

    @Override
    public IAnalysis getQccQyMh() {
        return new QccQyMhAnalysis();
    }

    @Override
    public IAnalysis getQccQyJz() {
        return new QccQyJzAnalysis();
    }
}
