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
 * Redis工具类
 *
 * @author 李磊
 */
@Slf4j
@Component
public class RedisUtil {

    private static RedisTemplate template;
    private static ValueOperations value;
    private static HashOperations hash;
    private static ListOperations list;
    private static SetOperations set;
    private static ZSetOperations zSet;

    @Autowired
    private void setRedisTemplate(RedisTemplate template) {
        RedisUtil.template = template;
        RedisUtil.value = template.opsForValue();
        RedisUtil.hash = template.opsForHash();
        RedisUtil.list = template.opsForList();
        RedisUtil.set = template.opsForSet();
        RedisUtil.zSet = template.opsForZSet();
    }

    /**
     * 读取缓存
     *
     * @param key -
     * @return -
     */
    public static <V> V get(String key) {
        return (V) value.get(key);
    }

    /**
     * 写入缓存
     *
     * @param key   -
     * @param value -
     * @return -
     */
    public static <V> void set(String key, V value) {
        RedisUtil.value.set(key, value);
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key   -
     * @param value -
     * @return -
     */
    public static <V> void set(String key, V value, long expireTime, TimeUnit timeUnit) {
        RedisUtil.value.set(key, value, expireTime, timeUnit);
    }

    /**
     * 批量删除对应的value
     *
     * @param keys -
     */
    public static <K> long delete(Collection<K> keys) {
        return template.delete(keys);
    }

    /**
     * 批量删除key
     *
     * @param pattern -
     */
    public static long deletePattern(String pattern) {
        return delete(keys(pattern));
    }

    /**
     * 删除对应的value
     *
     * @param key -
     */
    public static boolean delete(String key) {
        return template.delete(key);
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key -
     * @return -
     */
    public static boolean hasKey(String key) {
        return template.hasKey(key);
    }

    /**
     * 哈希添加
     *
     * @param key     -
     * @param hashKey -
     * @param value   -
     */
    public static <HV> void hashPut(String key, String hashKey, HV value) {
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     *
     * @param key     -
     * @param hashKey -
     * @return -
     */
    public static <HV> HV hashGet(String key, String hashKey) {
        return (HV) hash.get(key, hashKey);
    }

    /**
     * @param key        -
     * @param hashKeyMap -
     */
    public static <HV> void hashPutAll(String key, Map<String, HV> hashKeyMap) {
        hash.putAll(key, hashKeyMap);
    }

    /**
     * Hash数据类型 查找 key绑定的hash集合中hashKey的元素是否存在
     *
     * @param key     -
     * @param hashKey -
     * @return -
     */
    public static boolean hashHasKey(String key, String hashKey) {
        return hash.hasKey(key, hashKey);
    }

    /**
     * 获取hash所有值
     *
     * @param key -
     * @return -
     */
    public static <HV> Map<String, HV> hashGetAll(String key) {
        return hash.entries(key);
    }

    /**
     * 哈希数据删除
     *
     * @param key      -
     * @param hashKeys -
     */
    public static long hashDelete(String key, String... hashKeys) {
        return hash.delete(key, hashKeys);
    }

    /**
     * @param pattern -
     * @return -
     */
    public static Set<String> keys(String pattern) {
        return template.keys(pattern);
    }

    /**
     * 集合列表右添加
     *
     * @param key   -
     * @param value -
     */
    public static <V> long listRightPush(String key, V value) {
        return list.rightPush(key, value);
    }

    public static <V> long listLeftPush(String key, V value) {
        return list.leftPush(key, value);
    }

    /**
     * 获取指定位置下标list数据
     *
     * @param key   -
     * @param index -
     * @return -
     */
    public static <V> V listGet(String key, long index) {
        return (V) list.index(key, index);
    }

    /**
     * 集合列表获取
     *
     * @param key   -
     * @param begin 开始
     * @param end   结束
     * @return 0  -1表示全部
     */
    public static <V> List<V> listRange(String key, long begin, long end) {
        return list.range(key, begin, end);
    }

    public static <V> List<V> listGetAll(String key) {
        return listRange(key, 0L, -1L);
    }

    public static long listSize(String key) {
        return list.size(key);
    }

    /**
     * 删除集合
     *
     * @param key   -
     * @param count -
     * @param value -
     */
    public static <V> long listRemove(String key, long count, V value) {
        // 删除key中值为value的num个 返回删除的个数 如果没有这个元素则返回0
        return list.remove(key, count, value);
    }

    /**
     * 修改list集合中的某个数据
     *
     * @param key   -
     * @param index -
     * @param value -
     */
    public static <V> void listUpdate(String key, long index, V value) {
        list.set(key, index, value);
    }

    /**
     * set集合添加
     *
     * @param key   -
     * @param value -
     */
    public static <V> long setAdd(String key, V... value) {
        return set.add(key, value);
    }

    /**
     * 删除set中指定的一条数据
     *
     * @param key   -
     * @param value -
     */
    public static <V> long setRemove(String key, V value) {
        return set.remove(key, value);
    }

    /**
     * set集合获取
     *
     * @param key -
     * @return -
     */
    public static <V> Set<V> setMembers(String key) {
        return set.members(key);
    }

    /**
     * set集合获取size
     *
     * @param key -
     * @return -
     */
    public static long setSize(String key) {
        return set.size(key);
    }

    /**
     * set集合检查是否存在此值
     *
     * @param key -
     * @return -
     */
    public static <V> boolean setIsMember(String key, V value) {
        return set.isMember(key, value);
    }

    /**
     * 有序集合添加
     *
     * @param key   -
     * @param value -
     * @param score -
     */
    public static <V> boolean zSetAdd(String key, V value, double score) {
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
    public static <V> Set<V> zSetRangeByScore(String key, double min, double max) {
        return zSet.rangeByScore(key, min, max);
    }

    /**
     * 获取权重值
     *
     * @param key   -
     * @param value -
     * @return -
     */
    public static <V> double zSetScore(String key, V value) {
        return zSet.score(key, value);
    }

    public static <V> long zSetRemove(String key, V... values) {
        return zSet.remove(key, values);
    }

    /**
     * 查找value的索引
     *
     * @param key   -
     * @param value -
     * @return -
     */
    public static <V> long zSetReverseRank(String key, V value) {
        return zSet.reverseRank(key, value);
    }

    public static long zSetSize(String key) {
        return zSet.size(key);
    }

    public static long zSetRemoveRangeByScore(String key, double min, double max) {
        return zSet.removeRangeByScore(key, min, max);
    }

    /**
     * 获取key的过期时间
     *
     * @param key -
     * @return -
     */
    public static long getExpire(String key) {
        return template.getExpire(key);
    }

    /**
     * 递增
     *
     * @param key    -
     * @param number -
     * @return -
     */
    public static long increment(String key, long number) {
        return value.increment(key, number);
    }

    /**
     * 递增
     *
     * @param key -
     * @return -
     */
    public static long increment(String key) {
        return value.increment(key);
    }

    /**
     * 修改key
     *
     * @param oldKey -
     * @param newKey -
     */
    public static void rename(String oldKey, String newKey) {
        template.rename(oldKey, newKey);
    }
}