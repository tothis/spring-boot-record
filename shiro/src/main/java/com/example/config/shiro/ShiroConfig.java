package com.example.config.shiro;

import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李磊
 * @datetime 2020/1/19 12:37
 * @description
 */
@Slf4j
@Configuration
public class ShiroConfig {

    // 注入自定义realm
    @Bean
    public Realm realm(UserService userService) {
        UserRealm realm = new UserRealm(userService);
        // 设置用于匹配密码的CredentialsMatcher
        realm.setCredentialsMatcher(new CredentialsMatcher());
        return realm;
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        /**
         * 在引入spring aop后 在@Controller注解的类的方法中加入@RequiresRole注解 会导致该类所有方法无法映射请求 返回404
         * 如下设置为true可解决此问题
         */
        autoProxyCreator.setUsePrefix(true);
        return autoProxyCreator;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
        // 必须由`/`开头 `/**`匹配子孙路径
        chain.addPathDefinition("/captcha/*", "anon");
        chain.addPathDefinition("/webjars/**", "anon");
        chain.addPathDefinition("/static/**", "anon");
        chain.addPathDefinition("/no-auth", "anon");
        chain.addPathDefinition("/sign-*", "anon");
        chain.addPathDefinition("/test*/*", "anon");

        // 其余路径登录后才可访问
        chain.addPathDefinition("/**", "authc");
        return chain;
    }

    /**
     * 配置会话管理器 设定会话超时及保存
     *
     * @return
     */
    @Bean("sessionManager")
    public SessionManager shiroSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdCookie(sessionIdCookie());
        // 全局会话超时时间 单位毫秒 默认30分钟 暂时设置为10秒钟 用来测试
        // sessionManager.setGlobalSessionTimeout(10000);
        sessionManager.setGlobalSessionTimeout(1800000);
        // 是否开启删除无效的session对象 默认为true
        sessionManager.setDeleteInvalidSessions(true);
        // 是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 设置session失效的扫描时间 单位毫秒 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        // 暂时设置为 5秒 用来测试
        sessionManager.setSessionValidationInterval(3600000);
        // 隐藏url后的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * cookie记住我管理对象
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    private SimpleCookie sessionIdCookie() {
        // SESSIONID的cookie名称
        SimpleCookie simpleCookie = new SimpleCookie("session-id");
        // 设为true时 只能通过http访问 js无法访问 防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        // maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    /**
     * 记住我cookie对象
     */
    private SimpleCookie rememberMeCookie() {
        // 记住我cookie名称 对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // 设为true时 只能通过http访问 js无法访问 防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        // 记住我cookie生效时间30天 单位秒
        simpleCookie.setMaxAge(60 * 60 * 24 * 30);
        return simpleCookie;
    }
}