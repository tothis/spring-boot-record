//package com.example.controller;
//
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//import org.webjars.WebJarAssetLocator;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author 李磊
// * @datetime 2020/2/27 17:06
// * @description
// */
//@RestController
//public class WebJarController {
//
//    private final WebJarAssetLocator assetLocator = new WebJarAssetLocator();
//
//    private final String path = "static";
//
//    @GetMapping(path + "/{webjar}/**")
//    public ResponseEntity staticFile(HttpServletRequest request, @PathVariable String webjar) {
//        // <script th:src="@{/webjars/jquery/jquery.min.js}"></script>
//        // <script th:src="@{/static/jquery/jquery.min.js}"></script>
//        String url = request.getRequestURI();
//        String suffix = url.substring(url.lastIndexOf(".") + 1);
//        MultiValueMap multiValueMap = new HttpHeaders();
//        String[] suffixs = {"js", "css"};
//        String[] headers = {"application/javascript", "text/css"};
//        for (int i = 0; i < suffixs.length; i++) {
//            if (suffixs[i].equals(suffix)) {
//                multiValueMap.add(HttpHeaders.CONTENT_TYPE, headers[i]);
//                break;
//            }
//        }
//        try {
//            String fullPath = assetLocator.getFullPath(webjar, url.substring(url.lastIndexOf("/")));
//            return new ResponseEntity(new ClassPathResource(fullPath), multiValueMap, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity(multiValueMap, HttpStatus.NOT_FOUND);
//        }
//    }
//}