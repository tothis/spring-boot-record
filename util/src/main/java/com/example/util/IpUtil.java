package com.example.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Slf4j
public class IpUtil {

    // equals()函数 比较的类型可以是Object类型 比较字符串时区分长度区分大小写

    // equalsIgnoreCase()函数 比较的参数只能是字符串 比较字符串时区分长度不区分大小写

    public static final Pattern PATTERN = Pattern.compile(
            "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2" +
                    "[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\" +
                    "d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})");

    /**
     * 判断ip是否符合规则
     *
     * @param request
     * @return
     */
    public static boolean verify(HttpServletRequest request) {
        String ip = address(request);
        if (ip == null || ip.trim().isEmpty()) {
            log.debug("ip为空");
            return false;
        }

        boolean isValid = PATTERN.matcher(ip).matches();
        log.debug("ip : " + ip + (isValid ? "" : "不") + " 符合规则");
        return isValid;
    }

    /**
     * 获取用户真实IP地址 不使用request.getRemoteAddr() 原因是有可能用户使用了代理软件方式避免真实ip地址
     * 如果通过apache squid等软件多级反向代理的话 X-Forwarded-For的值为多个用','连接的ip值
     * <p>
     * 注意
     * <p>
     * 1.如下请求头都不是http协议里的标准请求头 也就是说是各个代理服务器自己规定的表示客户端地址的请求头
     * <p>
     * 2.请求头不是代理服务器一定会带上的 匿名代理就没有这些请求头 一般都可以自定义请求头设置
     * <p>
     * 3.不同的网络架构 判断请求头的顺序是不一样的 如下代码也不能确保获得的一定是客户端ip
     * <p>
     * 4.请求头都是可以伪造的 如果校验较严格的应用 如投票 要获取客户端ip 应该直接request.getRemoteAddr()
     * 获取到的可能是代理的ip而不是客户端ip 但这个获取到的ip基本上是不可能伪造的
     * 有分析说arp欺骗+syn有可能伪造此ip 如果真的可以 这是所有基于TCP协议都存在的漏洞 这个ip是tcp连接里的ip
     *
     * @return ip
     */
    public static String address(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (!ipIsEmpty(ip))
            // 多次反向代理后会有多个ip值 第一个ip才是真实ip
            if (ip.indexOf(',') != -1)
                return ip.substring(0, ip.indexOf(','));

        if (ipIsEmpty(ip)) {
            // apache http请求会有
            ip = request.getHeader("Proxy-client-IP");
            log.info("Proxy-client-IP ip: [{}]", ip);
        }
        if (ipIsEmpty(ip)) {
            // weblogic请求会有
            ip = request.getHeader("WL-Proxy-client-IP");
            log.info("WL-Proxy-client-IP ip: [{}]", ip);
        }
        if (ipIsEmpty(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.info("HTTP_CLIENT_IP ip: [{}]", ip);
        }
        if (ipIsEmpty(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info("HTTP_X_FORWARDED_FOR ip: [{}]", ip);
        }
        if (ipIsEmpty(ip)) {
            // nginx请求会有
            ip = request.getHeader("X-Real-IP");
            log.info("X-Real-IP ip: [{}]", ip);
        }
        if (ipIsEmpty(ip)) {
            ip = request.getRemoteAddr();
            // ip不存在
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                return "127.0.0.1";
            }
            log.info("remoteAddr ip: [{}]", ip);
        }
        System.out.println("获取客户端ip: " + ip);

        return ip;
    }

    private static boolean ipIsEmpty(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }
}