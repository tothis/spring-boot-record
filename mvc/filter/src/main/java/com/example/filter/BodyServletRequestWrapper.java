package com.example.filter;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 保存流 防止流第二次读取报错
 *
 * @author 李磊
 */
public class BodyServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public BodyServletRequestWrapper(final HttpServletRequest request) throws IOException {
        super(request);
        try (final ServletInputStream in = request.getInputStream()) {
            body = StreamUtils.copyToByteArray(in);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        try (final ByteArrayInputStream in = new ByteArrayInputStream(body)) {
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(final ReadListener readListener) {
                }

                @Override
                public int read() {
                    return in.read();
                }
            };
        }
    }
}
