//package com.example.controller;
//
//import com.example.model.Result;
//import com.example.model.User;
//import com.example.service.UserService;
//import com.example.util.Encryption;
//import com.example.util.IpUtil;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.LockedAccountException;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authc.credential.CredentialsMatcher;
//import org.apache.shiro.authz.annotation.RequiresGuest;
//import org.apache.shiro.subject.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//@Controller
//public class LoginController {
//
//    @Autowired
//    private RedisSessionDAO redisSessionDAO;
//    // 统计在线人数 不再使用shiroSessionListener监听
//    // private ShiroSessionListener shiroSessionListener;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private CredentialsMatcher matcher;
//
//    /**
//     * 访问项目根路径
//     *
//     * @return
//     */
//    // @RequiresUser
//    // @CrossOrigin
//    @GetMapping("") // 等同于'/'
//    public String root(Model model) {
////        Subject subject = SecurityUtils.getSubject();
////        String userName = subject.getPrincipal().toString();
////        if (userName == null) {
//        return "login";
////        } else {
////            return "redirect:/index";
////        }
//    }
//
//    /**
//     * 跳转到login页面
//     *
//     * @return
//     */
//    @GetMapping("login")
//    public String login(HttpServletRequest request, Model model) {
//        model.addAttribute("ip", IpUtil.address(request));
//        return "view/login";
//    }
//
//    @GetMapping("index1")
//    public String data(Model model) {
//        model.addAttribute("data", "data");
//        return "index";
//    }
//
//    /**
//     * 用户登录
//     *
//     * @param request
//     * @param userName
//     * @param password
//     * @param rememberMe
//     * @param captcha
//     * @param model
//     * @return
//     */
//    // 此方法支持localhost:9000跨域访问
//    @CrossOrigin(origins = {"http://localhost:9000", "null"})
//    @ResponseBody
//    @PostMapping("login")
//    public Result loginUser(HttpServletRequest request
//            , String userName, String password
//            , boolean rememberMe, String captcha, Model model) {
//
//        Object o = SecurityUtils.getSubject().getPrincipal();
//
//        // 校验验证码
////        String sessionCaptcha = (String) SecurityUtils.getSubject().getSession().getAttribute(CaptchaController.KEY_CAPTCHA);
////        if (captcha == null || !captcha.equalsIgnoreCase(sessionCaptcha)) {
////            return Result.fail("验证码错误！");
////        }
//
//        UsernamePasswordToken usernamePasswordToken = new ShiroLoginToken(userName, password, rememberMe, null, null);
//        Subject subject = SecurityUtils.getSubject();
//        try {
//            // 登录操作
//            subject.login(usernamePasswordToken);
//        } catch (Exception e) {
//
//            // 登录失败从request中获取shiro处理的异常信息 shiroLoginFailure:就是shiro异常类的全类名
//            String exception = (String) request.getAttribute("shiroLoginFailure");
//
//            if (e instanceof UnknownAccountException) {
//                return Result.fail("用户名或密码错误！");
//            }
//
//            if (e instanceof IncorrectCredentialsException) {
//                return Result.fail("密码错误！");
//            }
//
////            if (e instanceof AuthenticationException) {
////                return Result.fail("用户名或密码错误！");
////            }
//
//            if (e instanceof LockedAccountException) {
//                return Result.fail("账号已被锁定 请联系管理员！");
//            }
//            // TODO 自定义异常失效
//            return Result.fail();
//        }
//        return Result.success();
//    }
//
//    /**
//     * 跳转到register页面
//     *
//     * @return
//     */
//    @RequiresGuest
//    @GetMapping("register")
//    public String register() {
//        return "register";
//    }
//
//    /**
//     * 用户注册
//     *
//     * @param request
//     * @param userName
//     * @param password
//     * @param captcha
//     * @param model
//     * @return
//     */
//    @RequiresGuest
//    @PostMapping("register")
//    public String registerUser(HttpServletRequest request
//            , String userName, final String password
//            , String captcha, Model model) {
//
//        model.addAttribute("ip", IpUtil.address(request));
//
////        // 校验验证码
////        String sessionCaptcha = (String) SecurityUtils.getSubject().getSession().getAttribute(CaptchaController.KEY_CAPTCHA);
////        if (null == captcha || !captcha.equalsIgnoreCase(sessionCaptcha)) {
////            model.addAttribute("msg", "验证码错误！");
////            return "register";
////        }
//
//        userService.save(new User() {{
//            // 对密码进行加密
//            setPassword(Encryption.md5(password));
//            setUserName(userName);
//        }});
//
//        // 返回注册页面
//        return "register";
//    }
//
//    @GetMapping("index")
//    public String index(HttpSession session, Model model) {
//        Subject subject = SecurityUtils.getSubject();
//        String userName = (String) subject.getPrincipal();
//        if (userName == null) {
//            return "login";
//        } else {
//            User user = userService.findByUserName(userName);
//            model.addAttribute("user", user);
//            model.addAttribute("count", redisSessionDAO.getActiveSessionsSize());
//            return "login";
//        }
//    }
//
//    /**
//     * 登出 这个方法没用到 用的是shiro默认的logout
//     *
//     * @param session
//     * @param model
//     * @return
//     */
//    @GetMapping("logout")
//    public String logout(HttpSession session, Model model) {
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
//        model.addAttribute("msg", "安全退出！");
//        return "login";
//    }
//
//    /**
//     * 跳转到无权限页面
//     *
//     * @param session
//     * @param model
//     * @return
//     */
//    @GetMapping("unauthorized")
//    public String unauthorized(HttpSession session, Model model) {
//        return "unauthorized";
//    }
//
//    /**
//     * 解除admin 用户的限制登录
//     * 写死的 方便测试
//     *
//     * @return
//     */
//    @GetMapping("unlock-account")
//    public String unlockAccount(Model model) {
//        model.addAttribute("message", "用户解锁成功");
//
//        matcher.unlockAccount("admin");
//
//        return "login";
//    }
//}