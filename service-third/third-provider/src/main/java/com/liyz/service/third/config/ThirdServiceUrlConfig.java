package com.liyz.service.third.config;

import com.bfd.facade.MerchantServer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/18 13:44
 */
@Slf4j
@Getter
@Configuration
public class ThirdServiceUrlConfig {

    /**
     * 企查查服务信息
     */
    @Value("${qcc.url}")
    private String qccUrl;
    @Value("${qcc.key}")
    private String qccKey;
    @Value("${qcc.secret.key}")
    private String qccSecretKey;

    /**
     * 法海信息
     */
    @Value("${fh.url}")
    private String fhUrl;
    @Value("${fh.auth.code}")
    private String fhAuthCode;

    /**
     * 鹏元信息
     */
    @Value("${py.url}")
    private String pyUrl;
    @Value("${py.account}")
    private String pyAccount;
    @Value("${py.secret}")
    private String pySecret;

    /**
     * 百融信息
     */
    @Value("${br.username}")
    private String brUserName;
    @Value("${br.password}")
    private String brPassword;
    @Value("${br.api.code}")
    private String brApiCode;
    @Value("${br.login.api}")
    private String brLoginApi;
    @Value("${br.api.name}")
    private String brApiName;
    private volatile String brTokenId;
    private MerchantServer ms = new MerchantServer();

    public String getBrTokenId() {
        if (StringUtils.isBlank(brTokenId)) {
            synchronized (ThirdServiceUrlConfig.class) {
                if (StringUtils.isBlank(brTokenId)) {
                    setBrTokenId();
                }
            }
        }
        return brTokenId;
    }

    private void setBrTokenId() {
        try {
            String login_res_str = ms.login(brUserName, brPassword, brLoginApi, brApiCode);
            log.info("br login info:{}", login_res_str);
            if (StringUtils.isNotBlank(login_res_str)) {
                JSONObject loginJson = JSONObject.fromObject(login_res_str);
                if (loginJson.containsKey("tokenid")){
                    brTokenId = loginJson.getString("tokenid");
                }
            }
        } catch (Exception e) {
            log.error("br login error", e);
        }
    }

    /**
     * 同盾信息
     */
    @Value("${td.url}")
    private String tdUrl;
    @Value("${td.code}")
    private String tdCode;
    @Value("${td.key}")
    private String tdKey;
    @Value("${td.appName}")
    private String tdAppName;
}
