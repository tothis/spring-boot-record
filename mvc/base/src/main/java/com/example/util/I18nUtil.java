package com.example.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 国际化工具
 *
 * @author 李磊
 */
@Component
public class I18nUtil {

    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        I18nUtil.messageSource = messageSource;
    }

    /**
     * @param code 对应 messages 配置的 key
     * @return -
     */
    public static String getMessage(String code) {
        return I18nUtil.getMessage(code, new Object[]{});
    }

    public static String getMessage(String code, Locale locale) {
        return I18nUtil.getMessage(code, null, "", locale);
    }

    public static String getMessage(String code, String defaultMessage) {
        return I18nUtil.getMessage(code, null, defaultMessage);
    }

    public static String getMessage(String code, String defaultMessage, Locale locale) {
        return I18nUtil.getMessage(code, null, defaultMessage, locale);
    }

    /**
     * @param code 对应 messages 配置的 key -
     * @param args 数组参数 -
     * @return -
     */
    public static String getMessage(String code, Object[] args) {
        return I18nUtil.getMessage(code, args, "");
    }

    public static String getMessage(String code, Object[] args, Locale locale) {
        return I18nUtil.getMessage(code, args, "", locale);
    }

    /**
     * @param code           对应 messages 配置的 key
     * @param args           数组参数
     * @param defaultMessage 没有设置 key 的时候的默认值
     * @return -
     */
    public static String getMessage(String code, Object[] args, String defaultMessage) {
        // 获取当前请求的 Locale 第一种方式 不依赖 request
        Locale locale = LocaleContextHolder.getLocale();
        // 获取当前请求的 Locale 第二种方式
        // Locale locale = RequestContextUtils.getLocale(request);
        return I18nUtil.getMessage(code, args, defaultMessage, locale);
    }

    /**
     * 指定语言
     *
     * @param code           -
     * @param args           -
     * @param defaultMessage -
     * @param locale         -
     * @return -
     */
    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }
}
