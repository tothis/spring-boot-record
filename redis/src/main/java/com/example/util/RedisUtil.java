package com.example.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 *
 * @author 李磊
 */
@Slf4j
@Component
public final class RedisUtil {

    private static RedisTemplate template;
    private static ValueOperations value;
    private static HashOperations hash;
    private static ListOperations list;
    private static SetOperations set;
    private static ZSetOperations zSet;

    private RedisUtil() {
    }

    /**
     * 读取缓存
     *
     * @param key -
     * @return -
     */
    public static <V> V get(final String key) {
        return (V) value.get(key);
    }

    /**
     * 写入缓存
     *
     * @param key   -
     * @param value -
     * @return -
     */
    public static <V> void set(final String key, final V value) {
        RedisUtil.value.set(key, value);
    }

    /**
     * 写入缓存并设置过期时间
     *
     * @param key   -
     * @param value -
     * @return -
     */
    public static <V> void set(final String key, final V value, final long expireTime, final TimeUnit timeUnit) {
        RedisUtil.value.set(key, value, expireTime, timeUnit);
    }

    /**
     * 批量删除指定 value
     *
     * @param keys -
     */
    public static long delete(final Collection<String> keys) {
        return template.delete(keys);
    }

    /**
     * 批量删除 key
     *
     * @param pattern -
     */
    public static long deletePattern(final String pattern) {
        return delete(keys(pattern));
    }

    /**
     * 删除指定 value
     *
     * @param key -
     */
    public static boolean delete(final String key) {
        return template.delete(key);
    }

    /**
     * 是否存在 key
     *
     * @param key -
     * @return -
     */
    public static boolean hasKey(final String key) {
        return template.hasKey(key);
    }

    /**
     * hash 添加
     *
     * @param key     -
     * @param hashKey -
     * @param value   -
     */
    public static <HV> void hashPut(final String key, final String hashKey, final HV value) {
        hash.put(key, hashKey, value);
    }

    /**
     * hash 获取数据
     *
     * @param key     -
     * @param hashKey -
     * @return -
     */
    public static <HV> HV hashGet(final String key, final String hashKey) {
        return (HV) hash.get(key, hashKey);
    }

    /**
     * @param key        -
     * @param hashKeyMap -
     */
    public static <HV> void hashPutAll(final String key, final Map<String, HV> hashKeyMap) {
        hash.putAll(key, hashKeyMap);
    }

    /**
     * hash 查看元素是否存在
     *
     * @param key     -
     * @param hashKey -
     * @return -
     */
    public static boolean hashHasKey(final String key, final String hashKey) {
        return hash.hasKey(key, hashKey);
    }

    /**
     * hash 获取所有值
     *
     * @param key -
     * @return -
     */
    public static <HV> Map<String, HV> hashGetAll(final String key) {
        return hash.entries(key);
    }

    /**
     * hash 数据删除
     *
     * @param key      -
     * @param hashKeys -
     */
    public static long hashDelete(final String key, final String... hashKeys) {
        return hash.delete(key, hashKeys);
    }

    /**
     * @param pattern -
     * @return -
     */
    public static Set<String> keys(final String pattern) {
        return template.keys(pattern);
    }

    /**
     * list 右添加
     *
     * @param key   -
     * @param value -
     */
    public static <V> long listRightPush(final String key, final V value) {
        return list.rightPush(key, value);
    }

    public static <V> long listLeftPush(final String key, final V value) {
        return list.leftPush(key, value);
    }

    /**
     * 获取 list 指定下标数据
     *
     * @param key   -
     * @param index -
     * @return -
     */
    public static <V> V listGet(final String key, final long index) {
        return (V) list.index(key, index);
    }

    /**
     * 集合列表获取
     *
     * @param key   -
     * @param begin 开始
     * @param end   结束
     * @return -
     */
    public static <V> List<V> listRange(final String key, final long begin, final long end) {
        return list.range(key, begin, end);
    }

    /**
     * 获取 list 所有数据
     * <p>
     * 0  -1表示全部
     *
     * @param key -
     * @return -
     */
    public static <V> List<V> listGetAll(final String key) {
        return listRange(key, 0L, -1L);
    }

    public static long listSize(final String key) {
        return list.size(key);
    }

    /**
     * 删除 list 中值为 value 的 num 个数据
     *
     * @param key   -
     * @param count -
     * @param value -
     */
    public static <V> long listRemove(final String key, final long count, final V value) {
        return list.remove(key, count, value);
    }

    /**
     * 修改 list 中某个数据
     *
     * @param key   -
     * @param index -
     * @param value -
     */
    public static <V> void listUpdate(final String key, final long index, final V value) {
        list.set(key, index, value);
    }

    /**
     * 添加 set
     *
     * @param key   -
     * @param value -
     */
    public static <V> long setAdd(final String key, final V... value) {
        return set.add(key, value);
    }

    /**
     * 删除 set 中指定数据
     *
     * @param key   -
     * @param value -
     */
    public static <V> long setRemove(final String key, final V... value) {
        return set.remove(key, value);
    }

    /**
     * 获取 set
     *
     * @param key -
     * @return -
     */
    public static <V> Set<V> setMembers(final String key) {
        return set.members(key);
    }

    /**
     * 获取 set size
     *
     * @param key -
     * @return -
     */
    public static long setSize(final String key) {
        return set.size(key);
    }

    /**
     * 检查 set 是否存在此值
     *
     * @param key -
     * @return -
     */
    public static <V> boolean setIsMember(final String key, final V value) {
        return set.isMember(key, value);
    }

    /**
     * 有序集合添加
     *
     * @param key   -
     * @param value -
     * @param score -
     */
    public static <V> boolean zSetAdd(final String key, final V value, final double score) {
        return zSet.add(key, value, score);
    }

    /**
     * 有序集合获取
     *
     * @param key -
     * @param min -
     * @param max -
     * @return -
     */
    public static <V> Set<V> zSetRangeByScore(final String key, final double min, final double max) {
        return zSet.rangeByScore(key, min, max);
    }

    /**
     * 获取权重值
     *
     * @param key   -
     * @param value -
     * @return -
     */
    public static <V> double zSetScore(final String key, final V value) {
        return zSet.score(key, value);
    }

    public static <V> long zSetRemove(final String key, final V... values) {
        return zSet.remove(key, values);
    }

    /**
     * 查找 value 索引
     *
     * @param key   -
     * @param value -
     * @return -
     */
    public static <V> long zSetReverseRank(final String key, final V value) {
        return zSet.reverseRank(key, value);
    }

    public static long zSetSize(final String key) {
        return zSet.size(key);
    }

    public static long zSetRemoveRangeByScore(final String key, final double min, final double max) {
        return zSet.removeRangeByScore(key, min, max);
    }

    /**
     * 获取 key 过期时间
     *
     * @param key -
     * @return -
     */
    public static long getExpire(final String key) {
        return template.getExpire(key);
    }

    /**
     * 递增
     *
     * @param key    -
     * @param number -
     * @return -
     */
    public static long increment(final String key, final long number) {
        return value.increment(key, number);
    }

    /**
     * 递增
     *
     * @param key -
     * @return -
     */
    public static long increment(final String key) {
        return value.increment(key);
    }

    /**
     * 修改 key
     *
     * @param oldKey -
     * @param newKey -
     */
    public static void rename(final String oldKey, final String newKey) {
        template.rename(oldKey, newKey);
    }

    @Autowired
    private void setRedisTemplate(final RedisTemplate template) {
        RedisUtil.template = template;
        RedisUtil.value = template.opsForValue();
        RedisUtil.hash = template.opsForHash();
        RedisUtil.list = template.opsForList();
        RedisUtil.set = template.opsForSet();
        RedisUtil.zSet = template.opsForZSet();
    }
}
