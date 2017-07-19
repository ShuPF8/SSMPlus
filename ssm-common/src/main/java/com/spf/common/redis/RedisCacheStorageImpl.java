package com.spf.common.redis;

import com.mysql.jdbc.StringUtils;
import com.spf.common.BaseUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.HashMap;
import java.util.Map;

/**
 * redis 缓存存储器实现
 * @Author SPF
 * @Date 2017/4/25
 */
@Service
public class RedisCacheStorageImpl implements RedisCacheStorage {

    /**
     * 日志记录
     */
    public static final Logger LOG = Logger.getLogger(RedisCacheStorageImpl.class);

    /**
     * redis 客户端
     */
    @Autowired
    private RedisClient redisClient;

    /**
     * 默认过时时间 1H
     */
    private static final int EXPRIE_TIME =3600*24;

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    /**
     *在redis数据库中插入 key  和value
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        //设置默认过时时间
        return set(key, value, EXPRIE_TIME);
    }

    /**
     * 在redis数据库中插入 key  和value 并且设置过期时间
     * @param key
     * @param value
     * @param exp 过期时间 s
     * @return
     */
    @SuppressWarnings("finally")
    public boolean set(String key, Object value, int exp) {
        Jedis jedis =null;
        //将key 和value  转换成 json 对象
        String jKey = BaseUtils.toJSONString(key);
        String jValue = BaseUtils.toJSONString(value);
        //操作是否成功
        boolean isSucess =true;
        if(StringUtils.isNullOrEmpty(key)){
            LOG.info("key is empty");
            return false;
        }
        try {
            //获取客户端对象
            jedis =redisClient.getResource();
            //执行插入
            jedis.setex(jKey, exp, jValue);
        } catch (JedisException e) {
            LOG.info("client can't connect server");
            isSucess =false;
            if(null !=jedis){
                //释放jedis对象
                redisClient.brokenResource(jedis);
            }
            return false;
        }finally{
            if(isSucess){
                //返还连接池
                redisClient.returnResource(jedis);
            }
            return true;
        }
    }

    /**
     * 根据key 去redis 中获取value
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object get(String key) {
        Jedis jedis =null;
        //将key 和value  转换成 json 对象
        String jKey =BaseUtils.toJSONString(key);
        Object jValue =null;
        //key 不能为空
        if(StringUtils.isNullOrEmpty(jKey)){
            LOG.info("key is empty");
            return null;
        }
        try {
            //获取客户端对象
            jedis =redisClient.getResource();
            //执行查询
            String value =  jedis.get(jKey);
            //判断值是否非空
            if(StringUtils.isNullOrEmpty(value)){
                return null;
            }else{
                jValue= BaseUtils.parseObject(value, Object.class);
            }
            //返还连接池
            redisClient.returnResource(jedis);
        } catch (JedisException e) {
            LOG.info("client can't connect server");
            if(null !=jedis){
                //释放jedis 对象
                redisClient.brokenResource(jedis);
            }
        }
        return jValue;
    }

    /**
     * 删除redis库中的数据
     * @param key
     * @return
     */
    @SuppressWarnings("finally")
    public boolean remove(String key) {
        Jedis jedis =null;
        //将key 和value  转换成 json 对象
        String jKey =BaseUtils.toJSONString(key);
        //操作是否成功
        boolean isSucess =true;
        if(StringUtils.isNullOrEmpty(jKey)){
            LOG.info("key is empty");
            return false;
        }
        try {
            //获取jedis
            jedis =redisClient.getResource();

            //执行删除
            jedis.del(jKey);
        } catch (JedisException e) {
            LOG.info("client can't connect server");
            isSucess =false;
            if(null !=jedis){
                //释放jedis 对象
                redisClient.brokenResource(jedis);
            }
            return false;
        }finally{
            if (isSucess) {
                //返还连接池
                redisClient.returnResource(jedis);
            }
            return true;
        }
    }

    /**
     * 设置哈希类型数据到redis 数据库
     * @param cacheKey 可以看做一张表
     * @param key   表字段
     * @param value
     * @return
     */
    @SuppressWarnings("finally")
    public boolean hset(String cacheKey, String key, Object value) {
        Jedis jedis =null;
        //将key 和value,cacheKey  转换成 json 对象
        String jcacheKey = BaseUtils.toJSONString(cacheKey);
        String jkey = BaseUtils.toJSONString(key);
        String jvalue = BaseUtils.toJSONString(value);

        //操作是否成功
        boolean isSucess =true;
        if (StringUtils.isNullOrEmpty(jcacheKey)) {
            LOG.info("cacheKey is empty");
            return false;
        }

        try{
            jedis = redisClient.getResource();
            //执行插入哈希
            jedis.hset(jcacheKey, jkey, jvalue);
        } catch (Exception e) {
            LOG.info("client can't connect server");
            isSucess =false;
            if (jedis != null) {
                //释放jedis 对象
                redisClient.brokenResource(jedis);
            }
            return false;
        } finally {
            if (isSucess) {
                //返还连接池
                redisClient.returnResource(jedis);
            }
        }
        return true;
    }

    /**
     * 获取哈希表数据类型的值
     * @param cacheKey
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object hget(String cacheKey, String key) {
        Jedis jedis = null;
        String jcacheKey = BaseUtils.toJSONString(cacheKey);
        String jkey = BaseUtils.toJSONString(key);

        Object jvalue = null;
        if (StringUtils.isNullOrEmpty(jcacheKey)) {
            //LOG.info("cacheKey is empty");
            return null;
        }
        try{
            //获取客户端对象
            jedis = redisClient.getResource();
            //执行查询
            String value = jedis.hget(jcacheKey, jkey);
            if (null == value) {
                return null;
            } else {
                jvalue = BaseUtils.parseObject(value, Object.class);
            }
        } catch (Exception e) {
            LOG.info("client can't connect server");
            if(null !=jedis){
                //释放jedis 对象
                redisClient.brokenResource(jedis);
            }
        } finally {
            //返还连接池
            redisClient.returnResource(jedis);
        }

        return jvalue;
    }

    /**
     * 获取哈希类型的数据
     * @param cacheKey
     * @return
     */
    public Map<String, Object> hget(String cacheKey) {
        String jcacheKey = BaseUtils.toJSONString(cacheKey);
        if (StringUtils.isNullOrEmpty(jcacheKey)) {
            LOG.info("cacheKey is empty!");
            return null;
        }
        Jedis jedis =null;
        Map<String,Object> result =null;
        try {
            jedis = redisClient.getResource();
            //获取列表集合
            Map<String,String> map = jedis.hgetAll(jcacheKey);
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (result == null) {
                        result =new HashMap<String,Object>();
                    }
                    String rkey =   BaseUtils.parseObject(entry.getKey(), String.class);
                    Object rvalue = BaseUtils.parseObject(entry.getValue(), Object.class);
                    result.put(rkey, rvalue);
                }
            }
        } catch (Exception e) {
            LOG.info("client can't connect server");
            if(null !=jedis){
                //释放jedis 对象
                redisClient.brokenResource(jedis);
            }
        } finally {
            //返还连接池
            redisClient.returnResource(jedis);
        }
        return result;
    }
}
