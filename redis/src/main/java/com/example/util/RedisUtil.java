package com.example.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 李磊
 * @datetime 2019/12/24 23:42
 * @description
 */
@Slf4j
@Component
public class RedisUtil {

    private static RedisTemplate redisTemplate;

    /**
     * 切换db
     */
    public static void switchDB(int index) {
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        LettuceConnectionFactory factory = (LettuceConnectionFactory) connectionFactory;
        factory.setDatabase(index);
        // 重置共享连接 下次访问时重新初始化
        factory.resetConnection();
        // 按新的设置初始化
        factory.afterPropertiesSet();
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public static <K, V> V get(K key) {
        return (V) redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public static <K, V> void set(K key, V value) {
        set(key, value, 0L, null);
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public static <K, V> void set(K key, V value, long expireTime, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value);
            if (expireTime > 0) redisTemplate.expire(key, expireTime, timeUnit);
        } catch (Exception e) {
            log.error("redis单key写入错误", e);
        }
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public static <K> void delete(K... keys) {
        for (K key : keys) {
            delete(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public static void deletePattern(String pattern) {
        Set<String> keys = keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public static <K> void delete(K key) {
        if (hasKey(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public static <K> boolean hasKey(K key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 哈希添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public static <H, HK, HV> void hashPut(H key, HK hashKey, HV value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public static <H, HK, HV> HV hashGet(H key, HK hashKey) {
        return (HV) redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * @param key
     * @param hashKeyMap
     */
    public static <H, HK, HV> void hashPutAll(H key, Map<HK, HV> hashKeyMap) {
        redisTemplate.opsForHash().putAll(key, hashKeyMap);
    }

    /**
     * Hash数据类型 查找 key绑定的hash集合中hashKey的元素是否存在
     *
     * @param key
     * @param hashKey
     * @return
     */
    public static <H, HK> boolean hashHasKey(H key, HK hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取hash所有值
     *
     * @param key
     * @return
     */
    public static <H, HK, HV> Map<HK, HV> hashGetAll(H key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 哈希数据删除
     *
     * @param key
     * @param hashKeys
     */
    public static <H, HK> void hashDelete(H key, HK... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * @param pattern
     * @return
     */
    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 集合列表右添加
     *
     * @param key
     * @param value
     */
    public static <K, V> void listRightPush(K key, V value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public static <K, V> void listLeftPush(K key, V value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * @param key
     * @return
     * @author 李磊
     * @datetime 2019/09/29 11:40
     * @description 获取指定位置下标list数据
     */
    public static <K, V> V listGet(K key, long index) {
        return (V) redisTemplate.opsForList().index(key, index);
    }

    /**
     * 集合列表获取
     *
     * @param key
     * @param begin 开始
     * @param end   结束
     * @return 0 -1表示全部
     */
    public static <K, V> List<V> listRange(K key, long begin, long end) {
        return redisTemplate.opsForList().range(key, begin, end);
    }

    public static <K, V> List<V> listGetAll(K key) {
        return listRange(key, 0L, -1L);
    }

    public static <K> long listSize(K key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 删除集合
     *
     * @param key
     * @param count
     * @param value
     */
    public static <K, V> long listRemove(K key, long count, V value) {
        // 删除key中值为value的num个 返回删除的个数 如果没有这个元素则返回0
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 修改list集合中的某个数据
     *
     * @param key
     * @param index
     * @param value
     */
    public static <K, V> void listUpdate(K key, long index, V value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * set集合添加
     *
     * @param key
     * @param value
     */
    public static <K, V> void setAdd(K key, V... value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 删除set中指定的一条数据
     *
     * @param key
     * @param value
     */
    public static <K, V> void setRemove(K key, V value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * set集合获取
     *
     * @param key
     * @return
     */
    public static <K, V> Set<V> setMembers(K key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * set集合获取size
     *
     * @param key
     * @return
     */
    public static <K> long setSize(K key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * set集合检查是否存在此值
     *
     * @param key
     * @return
     */
    public static <K, V, T> boolean setIsMember(K key, T value) {
        BoundSetOperations<K, V> boundSetOperations = redisTemplate.boundSetOps(key);
        return boundSetOperations.isMember(value);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param score
     */
    public static <K, V> void zSetAdd(K key, V value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 有序集合获取
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static <K, V> Set<V> zSetRangeByScore(K key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 获取权重值
     *
     * @param key
     * @param value
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> double zSetScore(K key, V value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    public static <K, V> void zSetRemove(K key, V... values) {
        redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 查找value的索引
     *
     * @param key
     * @param value
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> long zSetReverseRank(K key, V value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    public static <K> long zSetSize(K key) {
        return redisTemplate.opsForZSet().size(key);
    }

    public static <T> void decrBy(T key, long value) {
        redisTemplate.getConnectionFactory().getConnection().decrBy(
                redisTemplate.getKeySerializer().serialize(key), value
        );
    }

    public static <T> void incrBy(T key, long value) {
        redisTemplate.getConnectionFactory().getConnection().incrBy(
                redisTemplate.getKeySerializer().serialize(key), value
        );
    }

    public static <K> long zSetRemoveRangeByScore(K key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 获取key的过期时间
     *
     * @param key
     * @return
     */
    public static <K> long getExpire(K key) {
        return redisTemplate.opsForValue().getOperations().getExpire(key);
    }

    /**
     * 递增
     */
    public static long increment(String key, long number) {
        return redisTemplate.opsForValue().increment(key, number);
    }

    /**
     * 递增
     */
    public static long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    @Autowired
    private void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}