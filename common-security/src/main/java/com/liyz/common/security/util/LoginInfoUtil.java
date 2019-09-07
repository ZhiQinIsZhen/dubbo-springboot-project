package com.liyz.common.security.util;

import com.liyz.service.member.bo.UserInfoBO;
import org.springframework.stereotype.Service;

/**
 * 注释: 重token获取 userInfoBO 安全类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 17:54
 */
@Service
public class LoginInfoUtil {

    private ThreadLocal<UserInfoBO> userBOContainer = new ThreadLocal<>();

    /**
     * 获取登录user
     *
     * @return UserInfo
     */
    public UserInfoBO getUser() {
        return userBOContainer.get();
    }

    /**
     * 设置登录user
     *
     * @param user UserInfo
     */
    public void setUser(UserInfoBO user) {
        this.userBOContainer.set(user);
    }
}
