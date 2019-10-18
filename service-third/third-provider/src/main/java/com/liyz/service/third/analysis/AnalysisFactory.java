package com.liyz.service.third.analysis;

import com.liyz.service.third.analysis.br.BrGrfqzAnalysis;
import com.liyz.service.third.analysis.fh.FhGrcxAnalysis;
import com.liyz.service.third.analysis.fh.FhQycxAnalysis;
import com.liyz.service.third.analysis.py.PyGrxqAnalysis;
import com.liyz.service.third.analysis.qcc.QccQyJzAnalysis;
import com.liyz.service.third.analysis.qcc.QccQyMhAnalysis;
import com.liyz.service.third.analysis.td.TdGrfqzAnalysis;
import com.liyz.service.third.constant.ThirdDataType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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

    @Override
    public IAnalysis getQccDwtz() {
        return new QccQyMhAnalysis();
    }

    @Override
    public IAnalysis getFhGrcx() {
        return new FhGrcxAnalysis();
    }

    @Override
    public IAnalysis getFhQycx(ThirdDataType.FhDataType fhDataType) {
        return new FhQycxAnalysis(fhDataType);
    }

    @Override
    public IAnalysis getPyGrfqz(List<ThirdDataType.PyDataType> pyDataTypes) {
        return new PyGrxqAnalysis(pyDataTypes);
    }

    @Override
    public IAnalysis getBrGrfqz() {
        return new BrGrfqzAnalysis();
    }

    @Override
    public IAnalysis getTdGrfqz() {
        return new TdGrfqzAnalysis();
    }
}
