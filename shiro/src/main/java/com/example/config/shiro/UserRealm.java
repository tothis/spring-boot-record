package com.example.config.shiro;

import com.example.model.Role;
import com.example.model.User;
import com.example.service.UserService;
import com.example.util.StringUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 参照JDBCRealm创建 自定义查询用户信息 校验密码等功能
 */
public class UserRealm extends AuthorizingRealm {

    private final UserService userService;

    public UserRealm(UserService userService) {
        this.userService = userService;
    }

    // 定义如何获取用户的角色和权限的逻辑 给shiro做权限判断
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
        // null usernames are invalid
        if (collection == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        User user = (User) getAvailablePrincipal(collection);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Role> roleList = user.getRoleList();
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        roleList.forEach(role -> {
            roles.add(role.getRoleName());
            role.getPermissionList().forEach(permission
                    -> permissions.add(permission.getPermissionName()));
        });

        System.out.println("获取角色信息 " + roles);
        System.out.println("获取权限信息 " + permissions);
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    // 定义如何获取用户信息的业务逻辑 给shiro做登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 获取用户名密码 第一种方式
        // String username = (String) token.getPrincipal();
        // String password = new String((char[]) token.getCredentials());

        // 获取用户名 密码 第二种方式
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();

        if (StringUtil.isBlank(userName)) {
            throw new AccountException("用户名不能为空");
        }

        User user = userService.findByUserName(userName);

        if (user == null) {
            throw new UnknownAccountException("用户名不存在 [" + userName + "]");
        }
        if (user.getState().equals(1)) {
            throw new LockedAccountException("账号已被锁定 请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), super.getName());
        if (user.getSalt() != null) {
            info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
        }
        return info;
    }
}