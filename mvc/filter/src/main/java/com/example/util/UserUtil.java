package com.example.util;

import lombok.experimental.UtilityClass;

/**
 * @author 李磊
 * @since 1.0
 */
@UtilityClass
public class UserUtil {

    public Long getUserId() {
        return 1L;
        /*String token = ServletUtil.request().getHeader(CommonConstant.TOKEN_HEADER);
        if (StrUtil.isBlank(token)) {
            throw new GlobalException(MessageType.USER_TOKEN_BLANK);
        }
        return RedisUtil.get(RedisKeyConstant.TOKEN + token);*/
    }

    /*public User getUser() {
        return RedisUtil.get(RedisKeyConstant.USER + getUserId());
    }*/
}