package com.example.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @author 李磊
 * @datetime 2019/12/9 17:41
 * @description
 */
public class HttpUtil {

    private enum HttpMethod {
        GET, POST
    }

    private final static int CONNECT_TIMEOUT = 5000;

    private final static String DEFAULT_ENCODING = "UTF-8";

    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.79 Safari/537.36";

    public static String get(String urlPath, Map<String, String> param) {
        HttpURLConnection connection = null;
        try {
            // 服务地址
            URL url = new URL(urlPath + "?" + param(param));
            // 设定连接的相关参数
            connection = urlPath.indexOf("https") == 0
                    ? (HttpsURLConnection) url.openConnection()
                    : (HttpURLConnection) url.openConnection();
            // 设置是否向HttpURLConnection输出 post请求参数要放在http正文内需设为true 默认false
            connection.setDoOutput(false);
            // 设置是否从HttpURLConnection读入 默认为true
            connection.setDoInput(true);
            // 设置缓存不可用
            connection.setUseCaches(false);
            // 设置请求方法类型为 默认为GET 此处必须为大写
            connection.setRequestMethod(HttpMethod.GET.name());

            // 设为true 系统自动处理重定向 设为false 需要自己从http reply中分析新的url
            // 设置所有的http连接是否自动处理重定向
            connection.setFollowRedirects(false);
            // 设置本次连接是否自动处理重定向
            connection.setInstanceFollowRedirects(false);

            // 设置User-agent
            connection.setRequestProperty("User-Agent", USER_AGENT);
            // 设置连接超时
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            // 设置读取超时
            connection.setReadTimeout(CONNECT_TIMEOUT);
            // 设置content-type
            // connection.setRequestProperty("content-type", MediaType.APPLICATION_JSON_VALUE);
            // 开始连接
            connection.connect();
            // 获取服务端的反馈
            return response(connection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return null;
    }

    public static String post(String urlPath, Map<String, String> param) {
        HttpURLConnection connection = null;
        try {
            // 服务地址
            URL url = new URL(urlPath);
            // 设定连接的相关参数
            connection = urlPath.indexOf("https") == 0
                    ? (HttpsURLConnection) url.openConnection()
                    : (HttpURLConnection) url.openConnection();
            // 设置是否向HttpURLConnection输出 post请求参数要放在http正文内需设为true 默认false
            connection.setDoOutput(true);
            // 设置是否从HttpURLConnection读入 默认为true
            connection.setDoInput(true);
            // 设置缓存不可用
            connection.setUseCaches(false);
            // 设置请求方法类型为 默认为GET 此处必须为大写
            connection.setRequestMethod(HttpMethod.POST.name());
            // 设置连接超时
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            // 设置读取超时
            connection.setReadTimeout(CONNECT_TIMEOUT);
            // 获取输出流 connection.getOutputStream已经包含了connect方法的调用
            // 如果响应内容乱码在此处添加编码名称 new OutputStreamWriter(connection.getOutputStream(), "UTF-8")
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            // post方式使用write输出参数
            out.write(param(param));
            out.flush();
            out.close();
            // 获取服务端的反馈
            return response(connection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return null;
    }

    /**
     * 组合参数
     *
     * @param param
     * @return
     */
    private static String param(Map<String, String> param) {
        if (param == null || param.size() == 0) return "";
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            builder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        // 删除最后一个'&'字符
        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    /**
     * 从connection获取响应
     *
     * @param connection
     * @return
     */
    private static String response(HttpURLConnection connection) {
        try (
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                // InputStream inputStream = connection.getInputStream();
                // ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int length = 0;
                while ((length = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, length);
                }
                return new String(outputStream.toByteArray());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // // 使用reader.readLine()读取
        // String result = null;
        // try {
        //     BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), DEFAULT_ENCODING));
        //     String temp;
        //     while ((temp = reader.readLine()) != null) {
        //         result += temp;
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        // return result;

        return null;
    }

    public static void main(String[] args) {
        System.out.println(HttpMethod.GET.name());
    }
}