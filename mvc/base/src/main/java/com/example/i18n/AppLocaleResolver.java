package com.example.i18n;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class AppLocaleResolver implements LocaleResolver {

    public static final String LANGUAGE = "language";

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String baseLocale = request.getParameter(LANGUAGE);
        Locale locale = Locale.getDefault();
        if (baseLocale != null && !baseLocale.trim().isEmpty()) {
            String[] split = baseLocale.split("_");
            locale = new Locale(split[0], split[1]);
            // 将国际化语言保存到session
            HttpSession session = request.getSession();
            session.setAttribute(LANGUAGE, baseLocale);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}