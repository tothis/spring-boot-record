package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/6/23 11:36
 * @description
 */
@NoArgsConstructor
@Data
public class LoginUser implements UserDetails {

    private String userName;

    private String password;

    private List<GrantedAuthority> authorities;

    public LoginUser(User user) {
        userName = user.getUserName();
        password = user.getPassword();
    }

    // 权限信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    // 账号是否未过期 默认false
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账号是否未锁定 默认false
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 账号凭证是否未过期 默认false
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 是否启用 默认false
    @Override
    public boolean isEnabled() {
        return true;
    }
}