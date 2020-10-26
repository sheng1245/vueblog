package com.zts.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @date 2020/10/7
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
