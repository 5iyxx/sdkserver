package com.sdkserver.utils;

/**
 * 连接和使用redis资源的工具类
 * Created by xcc on 2016/12/27.
 */
import com.u8.server.cache.CacheChangeListener;
import com.u8.server.constants.GlobalConfig;
import com.sdkserver.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisUtils {

    /**
     * 数据源
     */
    private JedisPool jedisPool;

    @Autowired
    private SubThread subThread;

    /**
     * 获取数据库连接
     * @return conn
     */
    public Jedis getConnection() {
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        }
        return jedis;
    }

    public void subCacheChangeChannel() {
        subThread.start();
    }

    public void pubCacheChangeChannel(String opType, int key) {
        Jedis jedis = getConnection();
        jedis.publish(GlobalConfig.CACHE_CHANGE_CHANNEL, GlobalConfig.SERVER_ID + "_" + opType + "_" + key);
        closeConnection(jedis);
    }

    /**
     * 关闭数据库连接
     */
    public void closeConnection(Jedis jedis) {
        if (null != jedis) {
            try {
                jedisPool.returnResource(jedis);
            } catch (Exception e) {
                e.printStackTrace();
                jedisPool.returnBrokenResource(jedis);
            }
        }
    }

    /**
     * 设置连接池
     */
    public void setJedisPool(JedisPool JedisPool) {
        this.jedisPool = JedisPool;
    }

    /**
     * 关闭
     */
    public void destroy() {
        jedisPool.destroy();
    }

    /**
     * 获取连接池
     * @return 数据源
     */
    public JedisPool getJedisPool() {
        return jedisPool;
    }


}