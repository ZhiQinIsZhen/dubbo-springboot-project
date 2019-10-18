package com.liyz.service.third.analysis;

import com.liyz.service.third.constant.ThirdDataType;
import com.liyz.service.third.constant.ThirdType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/19 13:53
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalysisFactoryUtil {

    public static IAnalysis getAnalysisResult(ThirdType thirdType, String... params) {
        IAnalysis analysis;
        AnalysisFactory analysisFactory = AnalysisFactory.getInstance();
        switch (thirdType) {
            case QCC_QYMH:
                analysis = analysisFactory.getQccQyMh();
                break;
            case QCC_QYJZ:
                analysis = analysisFactory.getQccQyJz();
                break;
            case QCC_DWTZ:
                analysis = analysisFactory.getQccDwtz();
                break;
            case FH_GRXQ:
                analysis = analysisFactory.getFhGrcx();
                break;
            case FH_QYXQ:
                String dataType = null;
                if (params != null && params.length > 0) {
                    dataType = params[0];
                }
                analysis = analysisFactory.getFhQycx(ThirdDataType.FhDataType.getByCode(dataType));
                break;
            case PY_GRFQZ:
                List<ThirdDataType.PyDataType> pyDataTypes = null;
                if (params != null && params.length > 0) {
                    dataType = params[0];
                    if (StringUtils.isNotBlank(dataType)) {
                        pyDataTypes = new ArrayList<>();
                        String[] dataTypes = params[0].split(",");
                        for (String str : dataTypes) {
                            ThirdDataType.PyDataType pyDataType = ThirdDataType.PyDataType.getByCode(str);
                            if (pyDataType != null) {
                                pyDataTypes.add(pyDataType);
                            }
                        }
                    }
                }
                analysis = analysisFactory.getPyGrfqz(pyDataTypes);
                break;
            case BR_GRFQZ:
                analysis = analysisFactory.getBrGrfqz();
                break;
            default:
                throw new IllegalArgumentException("no this thirdType:" + thirdType.getCode() + "analysis");
        }
        return analysis;
    }
}
