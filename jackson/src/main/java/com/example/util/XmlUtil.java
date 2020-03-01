//package com.example.util;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//
//import java.io.IOException;
//
///**
// * @author 李磊
// * @datetime 2019/12/11 17:38:40
// * @description jackson xml工具类
// */
//public class XmlUtil {
//
//    private static final ObjectMapper MAPPER = new XmlMapper();
//
//    public static <T> String ObjectToXml(T value) {
//        try {
//            return MAPPER.writeValueAsString(value);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static <T> T xmlToObject(String content, Class<T> valueType) {
//        try {
//            return MAPPER.readValue(content, valueType);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}