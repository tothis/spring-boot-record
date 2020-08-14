package com.example.util;

import lombok.SneakyThrows;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.CharArrayReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author 李磊
 * @datetime 2020/6/8 13:32
 * @description
 */
@Component
public final class MyBatisUtil {
    private static final String prefix = "${spring.datasource.";
    private static String driver;
    private static String url;
    private static String userName;
    private static String password;

    /**
     * sql脚本要以';'结尾
     */
    @SneakyThrows
    public static void exec(String content, ExecType type) {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, userName, password);
        ScriptRunner runner = new ScriptRunner(connection);
        runner.setStopOnError(true);
        try {
            switch (type) {
                case FILE:
                    runner.runScript(new FileReader(ResourceUtils
                            .getFile("classpath:" + content)));
                    break;
                case SQL:
                    runner.runScript(new CharArrayReader(content.toCharArray()));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    @Value(prefix + "driver-class-name}")
    public void setDriver(String driver) {
        MyBatisUtil.driver = driver;
    }

    @Value(prefix + "url}")
    public void setUrl(String url) {
        MyBatisUtil.url = url;
    }

    @Value(prefix + "username}")
    public void setUserName(String userName) {
        MyBatisUtil.userName = userName;
    }

    @Value(prefix + "password}")
    public void setPassword(String password) {
        MyBatisUtil.password = password;
    }

    public enum ExecType {
        FILE, SQL
    }
}