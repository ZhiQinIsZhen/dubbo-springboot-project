package com.liyz.common.security.core;

import com.liyz.common.security.util.JwtAuthenticationUtil;
import com.liyz.common.security.util.LoginInfoUtil;
import com.liyz.service.member.bo.UserInfoBO;
import com.liyz.service.member.remote.RemoteUserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 17:50
 */
@AllArgsConstructor
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private RemoteUserInfoService remoteUserInfoService;
    private LoginInfoUtil loginInfoUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoBO userInfoBO = new UserInfoBO();
        userInfoBO.setLoginName(username);
        userInfoBO = remoteUserInfoService.getByCondition(userInfoBO);
        if (Objects.isNull(userInfoBO)) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        loginInfoUtil.setUser(userInfoBO);
        return JwtAuthenticationUtil.create(userInfoBO);
    }
}
