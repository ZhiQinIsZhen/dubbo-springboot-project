package com.liyz.service.push.service;

import com.alibaba.fastjson.JSON;
import com.liyz.service.push.model.bo.sub.UserInfo;
import com.liyz.service.push.model.bo.user.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * 注释:鉴权
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/6 10:46
 */
@Slf4j
@Service
public class UserService {

    private static final String url = "http://127.0.0.1:8093/user/id";

    @Autowired
    RestTemplate restTemplate;

    @Value("${jwt.header}")
    private String header;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    public long getUserId(LoginRequest loginRequest) {
        if (!Objects.isNull(loginRequest)) {
            if (StringUtils.isNoneBlank(loginRequest.getToken())) {
                /**
                 * 做解析token工作，比如调用认证中心
                 * dubbo cloud都是可以的，这里用到时http的方式
                 */
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.add(header, tokenHead + loginRequest.getToken());
                HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
                if (HttpStatus.OK == response.getStatusCode()) {
                    String result = response.getBody();
                    if (StringUtils.isNoneBlank(result)) {
                        UserInfo userInfo = JSON.parseObject(result, UserInfo.class);
                        if (Objects.nonNull(userInfo) && Objects.nonNull(userInfo.getData())) {
                            return userInfo.getData().longValue();
                        }
                    }
                }
            }
        }
        return -1L;
    }
}
