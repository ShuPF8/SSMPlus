package com.spf.common.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @Author SPF
 * @Date 2017/4/26
 */
public class redisTest {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        /** 1.
         * Redis Java String(字符串) 实例
         */
        /*System.out.println("Connection to server sucessfully");
        //设置 redis 字符串数据
        jedis.set("runoobkey", "Redis tutorial");
        // 获取存储的数据并输出
        System.out.println("Stored string in redis:: "+ jedis.get("runoobkey"));
        System.out.println("get wil:" + jedis.get("wil"));*/

        /**
         * 2.Redis Java List(列表) 实例
         */
        /*System.out.println("Connection to server sucessfully");
        //存储数据到列表中
        jedis.lpush("tutorial-list", "Redis");
        jedis.lpush("tutorial-list", "Mongodb");
        jedis.lpush("tutorial-list", "Mysql");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("tutorial-list", 0 ,5);
        for(int i=0; i<list.size(); i++) {
            System.out.println("Stored string in redis:: "+list.get(i));
        }*/

        /**
         * 3.Redis Java Keys 实例
         */
        System.out.println("Connection to server sucessfully");
        // 获取数据并输出
        Set<String> list = jedis.keys("*");
        for (String str : list) {
            System.out.println("List of stored keys:: "+str);
        }
        /*List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name","spf");
        map.put("age",22);
        map.put("sex","男");
        list.add(map);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("name","hl");
        map1.put("age",23);
        map1.put("sex","nv");
        list.add(map1);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("name","ly");
        map2.put("age",23);
        map2.put("sex","nv");
        list.add(map2);
        jedis.set("list", list.toString());*/
        System.out.println(jedis.get("list"));
        //jedis.del("list");
    }
}
