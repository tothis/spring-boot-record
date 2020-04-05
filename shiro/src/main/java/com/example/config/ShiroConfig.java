//package com.example.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.Filter;
//import java.util.LinkedHashMap;
//
///**
// * @author 李磊
// * @datetime 2020/1/19 12:37
// * @description
// */
//@Slf4j
//@Configuration
//public class ShiroConfig {
//
//    /**
//     * 名称必须为 shiroFilterFactoryBean
//     */
//    @Bean(name = "shiroFilterFactoryBean")
//    public ShiroFilterFactoryBean shiroFilter() {
//
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//
//        // 设置Shiro核心安全管理器SecurityManager
//        shiroFilterFactoryBean.setSecurityManager(securityManager());
//        // /login为后台的接口名 非页面 不设置默认为根目录下的'/login.jsp'页面
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        // /index为后台的接口名 非页面 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/");
//        // /unauthorized为后台的接口名 未授权界面 该配置无效 并不会进行页面跳转
//        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
//
//        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
//
//        // 限制同一帐号同时在线的个数
//        // filtersMap.put("kickout", kickoutSessionControlFilter());
//
//        // 配置自定义登出 覆盖 logout 之前默认的LogoutFilter
//        // filtersMap.put("logout", shiroLogoutFilter());
//
//        shiroFilterFactoryBean.setFilters(filtersMap);
//
//        // 配置访问权限 必须是LinkedHashMap 因为它必须保证有序
//        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        // 配置不登录可以访问的资源 anon 表示资源都可以匿名访问
//
//        // 配置记住我或认证通过可以访问的地址 必须以'/'开头
//        // filterChainDefinitionMap.put("/login", "anon");
//        filterChainDefinitionMap.put("/static/**", "anon");
//        filterChainDefinitionMap.put("/favicon.ico", "anon");
//
//        filterChainDefinitionMap.put("/register/**", "anon");
//
//        filterChainDefinitionMap.put("/tool/**", "anon");
//
////        // 解锁用户专用 测试用的
////        filterChainDefinitionMap.put("/unlockAccount", "anon");
//        filterChainDefinitionMap.put("/captcha/**", "anon");
//
////        // logout是shiro提供的过滤器,这是走自定义的 shiroLogoutFilter 上面有配置
////        filterChainDefinitionMap.put("/logout", "logout");
////
////        // 此时访问/user/delete需要delete权限,在自定义Realm中为用户授权
////        // filterChainDefinitionMap.put("/user/delete", "perms[\"user:delete\"]");
////
////        // 其他资源都需要认证  authc 表示需要认证才能进行访问 user表示配置记住我或认证通过可以访问的地址
////        // 如果开启限制同一账号登录,改为 .put("/**", "kickout,user");
////        filterChainDefinitionMap.put("/**", "kickout,user");
////        // 过滤链定义 从上向下顺序执行 将/**放在最下边
////        // filterChainDefinitionMap.put("/**", "user");
//
//        // filterChainDefinitionMap.put("/**", "authc,user");
//
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//
//        return shiroFilterFactoryBean;
//    }
//
//    /**
//     * 默认已有名为securityManager实例 如下改名为userSecurityManager
//     */
//    @Bean("userSecurityManager")
//    public SecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        // 设置realm 下面通过Authenticator设置多个realm
//        securityManager.setRealm(realm());
//        // securityManager.setRealms(Arrays.asList(jwtShiroRealm(), shiroRealm()));
//        // 自定义缓存实现
//        // securityManager.setCacheManager(shiroCacheManager());
//        // 自定义session管理
//        securityManager.setSessionManager(sessionManager());
//        // 注入记住我管理器
//        securityManager.setRememberMeManager(rememberMeManager());
//        return securityManager;
//    }
////
////    /**
////     * 配置LogoutFilter
////     *
////     * @return
////     */
//////    public ShiroLogoutFilter shiroLogoutFilter() {
//////        ShiroLogoutFilter shiroLogoutFilter = new ShiroLogoutFilter();
//////        // 配置登出后重定向的地址
//////        shiroLogoutFilter.setRedirectUrl("/login");
//////        return shiroLogoutFilter;
//////    }
////
////    /**
////     * FormAuthenticationFilter 过滤器 过滤记住我
////     *
////     * @return
////     */
//////    @Bean
//////    public FormAuthenticationFilter formAuthenticationFilter() {
//////        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
//////        // 对应前端的checkbox的name = rememberMe
//////        formAuthenticationFilter.setRememberMeParam("rememberMe");
//////        return formAuthenticationFilter;
//////    }
////
////    /**
////     * shiro缓存管理器
////     * 需要添加到securityManager中
////     *
////     * @return
////     */
//////    @Bean
//////    public RedisCacheManager shiroCacheManager() {
//////        RedisCacheManager redisCacheManager = new RedisCacheManager();
//////        // redis中针对不同用户缓存
//////        redisCacheManager.setPrincipalIdFieldName("userName");
//////        // 用户权限信息缓存时间
//////        redisCacheManager.setExpire(200000);
//////        return redisCacheManager;
//////    }
////
////    /**
////     * SessionManager实例
////     */
////    @Bean
////    public DefaultWebSessionManager sessionManager() {
////        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
////        // sessionManager.setSessionDAO(redisSessionDAO());
////        // 设置session会话的全局过期时间(亳秒为单位) 默认30分钟
////        // sessionManager.setGlobalSessionTimeout(1000);
////        // 负数为永不过时 注意值会被转换成秒 小数会被省略 因此最大为-1000
////        // sessionManager.setGlobalSessionTimeout(-1000);
////        sessionManager.setSessionValidationSchedulerEnabled(true);
////        return sessionManager;
////    }
////
//
////
////    /**
////     * 配置保存sessionId的cookie
////     * 默认为:JSESSIONID 问题:与SERVLET容器名冲突 重新定义为id
////     *
////     * @return
////     */
////    @Bean//("sessionIdCookie")
////    public SimpleCookie sessionIdCookie() {
////        // 这个参数是cookie的名称
////        SimpleCookie simpleCookie = new SimpleCookie("id");
////        // 设为true时 只能通过http访问 js无法访问 防止xss读取cookie
////        simpleCookie.setHttpOnly(true);
////        simpleCookie.setPath("/");
////        // maxAge=-1表示浏览器关闭时失效此Cookie
////        simpleCookie.setMaxAge(-1);
////        return simpleCookie;
////    }
////
////    /**
////     * cookie对象
////     */
////    @Bean
////    public SimpleCookie rememberMeCookie() {
////        // 这个参数是cookie的名称 对应前端的checkbox的name = rememberMe
////        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
////        // 设为true时 只能通过http访问 js无法访问 防止xss读取cookie
////        simpleCookie.setHttpOnly(true);
////        simpleCookie.setPath("/");
////        // 记住我cookie生效时间30天 单位秒
////        simpleCookie.setMaxAge(60 * 60 * 24 * 30);
////        return simpleCookie;
////    }
////
////    /**
////     * cookie记住我管理对象
////     */
////    @Bean
////    public CookieRememberMeManager rememberMeManager() {
////        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
////        cookieRememberMeManager.setCookie(rememberMeCookie());
////        // rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
////        // cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
////        return cookieRememberMeManager;
////    }
////
////    /**
////     * SecurityManager接口继承了Authenticator
////     * 另外还有一个ModularRealmAuthenticator实现
////     * 其委托给多个Realm进行验证 验证规则通过AuthenticationStrategy接口指定
////     * 默认提供的实现
////     * <p>
////     * FirstSuccessfulStrategy 只要有一个Realm验证成功即可 只返回第一个Realm身份验证成功的认证信息 忽略其它
////     * <p>
////     * AtLeastOneSuccessfulStrategy 只要有一个Realm验证成功即可 和FirstSuccessfulStrategy不同 返回所有Realm身份验证成功的认证信息
////     * <p>
////     * AllSuccessfulStrategy 所有Realm验证成功才算成功 返回所有Realm身份验证成功的认证信息
////     */
////    @Bean
////    public Authenticator authenticator() {
////        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
////        // authenticator.setRealms(Arrays.asList(jwtShiroRealm(), shiroRealm()));
////        // ModularRealmAuthenticator默认使用AtLeastOneSuccessfulStrategy策略
////        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
////        return authenticator;
////    }
////
//////    @Bean
//////    protected SessionStorageEvaluator sessionStorageEvaluator() {
//////        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
//////        // 返回false时表示Subject不可以存储到Session中
//////        sessionStorageEvaluator.setSessionStorageEnabled(false);
//////        return sessionStorageEvaluator;
//////    }
////
////    //@Bean//("jwtRealm")
////    //public Realm jwtShiroRealm() {
////    //    return new JWTShiroRealm();
////    //}
////
////    /**
////     * 身份认证realm 账号密码校验 权限
////     */
////    @Bean
////    public UserRealm realm() {
////        UserRealm realm = new UserRealm();
////        realm.setCachingEnabled(true);
////        // 启用授权缓存 默认false
////        realm.setAuthorizationCachingEnabled(true);
////        // 授权信息缓存名称
////        realm.setAuthorizationCacheName("limit");
////        // 启用身份验证缓存 默认false
////        realm.setAuthenticationCachingEnabled(true);
////        // 验证信息缓存名称
////        realm.setAuthenticationCacheName("verify");
////        // 配置自定义密码比较器
////        // realm.setCredentialsMatcher(credentialsMatcher());
////        return realm;
////    }
////
////    /**
////     * RedisSessionDAO shiroSessionDao实现
////     */
//////    @Bean
//////    public RedisSessionDAO redisSessionDAO() {
//////        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//////        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
//////        // redisSessionDAO.setKeyPrefix("shiro-session:");
//////        // session在redis中的保存时间 最好大于session会话超时时间
//////        redisSessionDAO.setExpire(12000);
//////        return redisSessionDAO;
//////    }
////
////    /**
////     * 设置JSESSIONID
////     */
////    @Bean
////    public SessionIdGenerator sessionIdGenerator() {
////        return e -> UUID.randomUUID().toString().replace("-", "") + ":id";
////        // return new JavaUuidSessionIdGenerator();
////    }
////
////    /**
////     * 授权所用配置
////     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
////     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
////     */
////    @Bean
////    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
//////        return new DefaultAdvisorAutoProxyCreator() {{
//////            setProxyTargetClass(true); // 开启aop自动代理
//////        }};
////        return new DefaultAdvisorAutoProxyCreator() {{
////            setProxyTargetClass(true); // 开启aop自动代理
////        }};
////    }
////
////    /**
////     * 开启注解功能 可通过注解控制权限 需要开启spring aop
////     */
//////    @Bean
//////    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
//////        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//////        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
//////        return authorizationAttributeSourceAdvisor;
//////    }
////
//
//    /**
//     * shiro生命周期处理器 设置为静态 可以解决@value注解无法读取application.yml中的配置
//     */
//    @Bean
//    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
////
////    /**
////     * 解决 无权限页面不跳转 shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized") 无效
////     * shiro的源代码ShiroFilterFactoryBean.Java定义的filter必须满足filter instanceof AuthorizationFilter
////     * 只有perms roles ssl rest port才是属于AuthorizationFilter 而anon authcBasic auchc user是AuthenticationFilter
////     * 所以unauthorizedUrl设置后页面不跳转 Shiro注解模式下 登录失败与没有权限都是通过抛出异常
////     * 并且默认并没有去处理或者捕获这些异常 在SpringMVC下需要配置捕获相应异常来通知用户信息
////     *
////     * @return
////     */
//////    @Bean
//////    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
//////        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
//////        Properties properties = new Properties();
//////        // 这里的 /unauthorized 是页面 不是访问的路径
//////        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/unauthorized");
//////        properties.setProperty("org.apache.shiro.authz.UnauthenticatedException", "/unauthorized");
//////        simpleMappingExceptionResolver.setExceptionMappings(properties);
//////        return simpleMappingExceptionResolver;
//////    }
////
////    /**
////     * 让某个实例的某个方法的返回值注入为Bean的实例
////     * Spring静态注入
////     *
////     * @return
////     */
//////    @Bean
//////    public MethodInvokingFactoryBean getMethodInvokingFactoryBean() {
//////        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
//////        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
//////        factoryBean.setArguments(new Object[]{securityManager()});
//////        return factoryBean;
//////    }
////
//////    /**
//////     * 配置session监听
//////     *
//////     * @return
//////     */
//////    @Bean//("sessionListener")
//////    public ShiroSessionListener sessionListener() {
//////        return new ShiroSessionListener();
//////    }
//////
//////    @Bean//("sessionFactory")
//////    public ShiroSessionFactory sessionFactory() {
//////        ShiroSessionFactory sessionFactory = new ShiroSessionFactory();
//////        return sessionFactory;
//////    }
//////
//////    /**
//////     * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
//////     * MemorySessionDAO 直接在内存中进行会话维护
//////     * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护 默认情况下使用MapCache实现 内部使用ConcurrentHashMap保存缓存的会话
//////     *
//////     * @return
//////     */
//////    @Bean
//////    public SessionDAO sessionDAO() {
//////        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//////        // session在redis中的保存时间,最好大于session会话超时时间
//////        redisSessionDAO.setExpire(12000);
//////        return redisSessionDAO;
//////    }
//////
//////    /**
//////     * 配置会话管理器 设定会话超时及保存
//////     *
//////     * @return
//////     */
////    @Bean//("sessionManager")
////    public SessionManager shiroSessionManager() {
////        return new DefaultSecurityManager();
//////        Collection<SessionListener> listeners = new ArrayList<>();
//////        // 配置监听
//////        // listeners.add(sessionListener());
//////        sessionManager.setSessionListeners(listeners);
//////        sessionManager.setSessionIdCookie(sessionIdCookie());
//////        sessionManager.setSessionDAO(sessionDAO());
//////        sessionManager.setCacheManager(shiroCacheManager());
//////        sessionManager.setSessionFactory(sessionFactory());
//////
//////        // 全局会话超时时间（单位毫秒） 默认30分钟  暂时设置为10秒钟 用来测试
//////        sessionManager.setGlobalSessionTimeout(1800000);
//////        // 是否开启删除无效的session对象  默认为true
//////        sessionManager.setDeleteInvalidSessions(true);
//////        // 是否开启定时调度器进行检测过期session 默认为true
//////        sessionManager.setSessionValidationSchedulerEnabled(true);
//////        // 设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
//////        // 设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
//////        // 暂时设置为 5秒 用来测试
//////        sessionManager.setSessionValidationInterval(3600000);
//////        // 隐藏url后的JSESSIONID
//////        sessionManager.setSessionIdUrlRewritingEnabled(false);
//////        return sessionManager;
////    }
//////
//////    /**
//////     * 并发登录控制 限制同一账号登录同时登录人数控制
//////     *
//////     * @return
//////     */
//////    @Bean
//////    public KickoutSessionControlFilter kickoutSessionControlFilter() {
//////        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
//////        // 用于根据会话ID 获取会话进行踢出操作的
//////        kickoutSessionControlFilter.setSessionManager(sessionManager());
//////
//////        // 是否踢出后来登录的 默认是false 即后者登录的用户踢出前者登录的用户
//////        kickoutSessionControlFilter.setKickoutAfter(false);
//////        // 同一个用户最大的会话数 默认1 比如2的意思是同一个用户允许最多同时两个人登录
//////        kickoutSessionControlFilter.setMaxSession(1);
//////        // 被踢出后重定向到的地址
//////        kickoutSessionControlFilter.setKickoutUrl("/login?kickout=1");
//////        return kickoutSessionControlFilter;
//////    }
//////
//////    /**
//////     * 配置密码比较器
//////     *
//////     * @return
//////     */
//////    @Bean//("credentialsMatcher")
//////    public CredentialsMatcher credentialsMatcher() {
//////        return new CredentialsMatcher();
//////    }
//
//}