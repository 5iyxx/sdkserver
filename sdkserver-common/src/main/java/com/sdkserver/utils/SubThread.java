package com.sdkserver.utils;

import com.u8.server.cache.CacheChangeListener;
import com.u8.server.constants.GlobalConfig;
import com.sdkserver.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * Created by xcc on 2016/12/28.
 */
@Service("subThread")
public class SubThread extends Thread {

    @Autowired
    private CacheChangeListener cacheChangeListener;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void run() {
        while(true){
            Jedis jedis = null;
            try {
                jedis = redisUtils.getConnection();
                jedis.subscribe(cacheChangeListener, GlobalConfig.CACHE_CHANGE_CHANNEL);
            } catch (Exception e) {
                Log.e(String.format("subsrcibe channel error, %s", e));
            }

            // 处理redis宕机或者重启，此时每隔一段时间重连
            try{
                Thread.sleep(1000);
            } catch(Exception unused){}

            redisUtils.closeConnection(jedis);
        }
    }
}