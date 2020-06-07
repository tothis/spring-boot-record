package com.example.model;

import lombok.Data;

/**
 * @author 李磊
 * @datetime 2020/5/8 19:10
 * @description {"content": "1", "inner1s": [{"content": "2", "inner2s": [{"content": "3"}]}], "inner2s": [{"content": "4"}]}
 */
@Data
public class Inner {
    private String content;
    private Inner1[] inner1s;
    private Inner1.Inner2[] inner2s;

    @Data // spring mvc参数绑定 内部类需声明为静态
    private static class Inner1 {
        private String content;
        private Inner2[] inner2s;

        @Data
        private static class Inner2 {
            private String content;
        }
    }
}