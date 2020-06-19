package com.example.config.security;

import com.example.model.Permission;
import com.example.model.Role;
import com.example.repository.PermissionRepository;
import com.example.util.StringUtil;
import com.example.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 获取接口所需角色
 */
@Component
public class UserSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private PermissionRepository permissionRepository;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        UserUtil.getCurrentUser();

        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Permission> permissions = permissionRepository.findAll();
        for (Permission permission : permissions) {
            if (antPathMatcher.match(permission.getUrl(), requestUrl)) {
                List<Role> roles = permission.getRoleList();
                String[] result = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    String roleName = roles.get(i).getRoleName();
                    if (StringUtil.isNotBlank(roleName))
                        result[i] = roleName;
                }
                return SecurityConfig.createList(result);
            }
        }
        // createList返回null或空字符串直接报错
        // return SecurityConfig.createList("");
        // return SecurityConfig.createList(null);
        // 返回null或空集合 表示当前请求不需任何登录就可访问
        // return SecurityConfig.createList();
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}