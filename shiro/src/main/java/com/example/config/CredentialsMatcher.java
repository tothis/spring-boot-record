package com.example.config;

import com.example.util.Encryption;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 自定义密码比较器
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // 获得用户输入密码
        String password = new String(((UsernamePasswordToken) token).getPassword());

        // 获得数据库中的密码
        String dbPassword = (String) info.getCredentials();

        // 密码比对
        return Encryption.md5Equals(Encryption.md5(password), dbPassword);
    }
}