package com.example.model;

import lombok.Data;

/**
 * {
 * "content":"1",
 * "inner1s":[ { "content":"2", "inner2s":[ { "content":"3" } ] } ],
 * "inner2s":[ { "content":"4" } ]
 * }
 *
 * @author 李磊
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
