package com.example.controller;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李磊
 * @datetime 2020/5/13 15:52
 * @description https://github.com/spring-projects/spring-mobile-samples
 */
@RequestMapping("mobile")
@Controller
public class MobileController {

    @GetMapping
    public String index(SitePreference site) {
        System.out.println(site.isMobile());
        return "index";
    }

    @ResponseBody
    @GetMapping("info")
    public Map<String, Object> info(HttpServletRequest request, SitePreference site, Device device) {
        // 使用工具类获取
        // Device currentDevice = DeviceUtils.getCurrentDevice(request);
        // SitePreference currentSitePreference = SitePreferenceUtils.getCurrentSitePreference(request);
        System.out.println("isMobile : " + site.isMobile());
        System.out.println("isTablet : " + site.isTablet());
        System.out.println("isNormal : " + site.isNormal());

        System.out.println("isMobile : " + device.isMobile());
        System.out.println("isTablet : " + device.isTablet());
        System.out.println("isNormal : " + device.isNormal());

        System.out.println("platform : " + device.getDevicePlatform());
        System.out.println("siteName : " + site.name());
        return new HashMap<String, Object>() {{
            put("site", site);
            put("device", device);
        }};
    }
}