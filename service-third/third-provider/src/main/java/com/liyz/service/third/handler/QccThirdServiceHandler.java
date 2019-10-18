package com.liyz.service.third.handler;

import com.liyz.service.third.constant.ThirdConstant;
import com.liyz.service.third.constant.ThirdType;
import com.liyz.service.third.handler.abs.AbstractThirdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 注释: 企查查handler
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/17 17:12
 */
@Slf4j
public class QccThirdServiceHandler extends AbstractThirdService {

    public QccThirdServiceHandler() {
        setLoadDataSource(false);
    }

    public QccThirdServiceHandler(boolean saveEs) {
        this();
        setSaveEs(saveEs);
    }

    @Override
    protected void addPageInfoToParams(Map<String, Object> params, int pageNo, int pageSize) {
        params.put("pageIndex", String.valueOf(getPageNoSize().get().getLeft()));
        params.put("pageSize", String.valueOf(getPageNoSize().get().getRight()));
        super.addPageInfoToParams(params, pageNo, pageSize);
    }

    @Override
    protected Map<String, String> setRequestHeads(Map<String, String> heads) throws Exception {
        if (heads == null) {
            heads = new HashMap<>();
        }
        String timestamp = String.valueOf((System.currentTimeMillis() / 1000));
        String token = DigestUtils.md5DigestAsHex(getThirdServiceUrlConfig().getQccKey().concat(timestamp)
                .concat(getThirdServiceUrlConfig().getQccSecretKey()).getBytes()).toUpperCase();
        heads.put("Token", token);
        heads.put("Timespan", timestamp);
        return heads;
    }

    @Override
    protected Map<String, Object> setRequestParams(Map<String, Object> params) {
        if (params == null) {
            throw new IllegalArgumentException();
        }
        params.put("key", getThirdServiceUrlConfig().getQccKey());
        return params;
    }

    @Override
    protected void checkThirdType(ThirdType thirdType) {
        if (ThirdConstant.QCC_CODE.intValue() != thirdType.getCode()/10000) {
            throw new IllegalArgumentException("thirdType is not qcc service type!");
        }
    }

    @Override
    protected String setThirdServiceUrl(ThirdType thirdType) {
        return getThirdServiceUrlConfig().getQccUrl() + thirdType.getSubUrl();
    }
}
