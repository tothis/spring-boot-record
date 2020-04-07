package com.example.realm;

import com.example.model.Permission;
import com.example.model.Role;
import com.example.model.User;
import com.example.service.PermissionService;
import com.example.service.RoleService;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 李磊
 * @datetime 2020/2/10 15:26
 * @description
 */
@Slf4j
public class UserRealm1 extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 授权用户权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("查询权限方法调用");
        log.info("多个realm [{}]", principals);
        log.info("primaryPrincipal [{}]", principals.getPrimaryPrincipal());
        // 获取用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //添加角色
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<String> roles = user.getRoleList().stream().map(Role::getRoleName).collect(Collectors.toList());
        if (roles == null) {
            List<Role> roleList = userService.findRolesById(user.getId());
            user.setRoleList(roleList);
            // 设置用户角色
            authorizationInfo.addRoles(roleList.stream()
                    .map(Role::getRoleName).collect(Collectors.toList()));
            Set<String> permissions = new HashSet<>();
            for (int i = 0; i < roleList.size(); i++) {
                // 获取用户权限 添加权限
                permissions.addAll(roleList.get(i).getPermissionList()
                        .stream().map(Permission::getPermission).collect(Collectors.toSet()));
            }
        } else {
            authorizationInfo.addRoles(roles);
            Set<String> permissions = new HashSet<>();
            for (int i = 0; i < user.getRoleList().size(); i++) {
                // 获取用户权限 添加权限
                permissions.addAll(user.getRoleList().get(i).getPermissionList()
                        .stream().map(Permission::getPermission).collect(Collectors.toSet()));
            }
            authorizationInfo.setStringPermissions(permissions);
        }
        return authorizationInfo;
    }

    /**
     * 验证用户身份
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户名密码 第一种方式
        // String username = (String) token.getPrincipal();
        // String password = new String((char[]) token.getCredentials());

        // 获取用户名 密码 第二种方式
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        String userName = usernamePasswordToken.getUsername();

        // 盐值
        // final String SALT = "\\@#$%^&*/";
        // 加密方式
//        final String ALGORITHM_NAME = "md5";
        // hash次数
//        final int HASH_ITERATIONS = 2;

        // 从数据库查询用户信息
        User user = userService.findByUserName(userName);

//        String password = new String(new SimpleHash(ALGORITHM_NAME, usernamePasswordToken.getPassword(),
//                ByteSource.Util.bytes(user.getSalt()), HASH_ITERATIONS).toHex());

        // 可以在这里直接对用户名校验 或调用CredentialsMatcher校验
        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误");
        }
        //这里将 密码对比 注销掉,否则 无法锁定  要将密码对比 交给 密码比较器
        //if (!password.equals(user.getPassword())) {
        //    throw new IncorrectCredentialsException("用户名或密码错误！");
        //}
        if (user.getState().equals(1)) {
            throw new LockedAccountException("账号已被锁定 请联系管理员");
        }

        return new SimpleAuthenticationInfo(user, user.getPassword()
                // , new ShiroByteSource(user.getUserName()) // 盐值
                , super.getName());
    }
}