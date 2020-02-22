package com.example.format;

import org.beetl.core.Format;

public class StringFormat implements Format {

    @Override
    public Object format(Object data, String pattern) {
        return data + pattern;
    }
}