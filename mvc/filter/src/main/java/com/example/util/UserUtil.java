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
        Object o = RedisUtil.get(RedisKeyConstant.TOKEN + token);
        if (o == null) {
            throw new GlobalException(MessageType.USER_TOKEN_INVALID);
        }
        // redisTemplate获取Long值方式
        return ((Integer) o).longValue();*/
    }

    /*public User getUser() {
        return RedisUtil.get(RedisKeyConstant.USER + getUserId());
    }*/
}