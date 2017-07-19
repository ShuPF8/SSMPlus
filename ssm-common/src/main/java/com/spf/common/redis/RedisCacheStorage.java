package com.spf.common.redis;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 缓存存储接口
 * @Author SPF
 * @Date 2017/4/25
 */
@Repository
public interface RedisCacheStorage{
    /**
     * 在redis数据库中插入 key  和value
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, Object value);

    /**
     * 在redis数据库中插入 key  和value 并且设置过期时间
     * @param key
     * @param value
     * @param exp 过期时间 s
     * @return
     */
    boolean set(String key, Object value, int exp);
    /**
     * 根据key 去redis中获取value
     * @param key
     * @return
     */
    Object get(String key);
    /**
     * 删除redis库中的数据
     * @param key
     * @return
     */
    boolean remove(String key);
    /**
     * 设置哈希类型数据到redis 数据库
     * @param cacheKey 可以看做一张表
     * @param key   表字段
     * @param value
     * @return
     */
    boolean hset(String cacheKey, String key, Object value);
    /**
     * 获取哈希表数据类型的值
     * @param cacheKey
     * @param key
     * @return
     */
    Object hget(String cacheKey, String key);
    /**
     * 获取哈希类型的数据
     * @param cacheKey
     * @return
     */
    Map<String,Object> hget(String cacheKey);
}
