package com.sdkserver.utils;

import com.u8.server.cache.CacheManager;
import com.u8.server.data.UChannelMaster;
import com.u8.server.data.UGame;
import com.sdkserver.log.Log;
import java.util.Calendar;

/***
 * 这个是一个简单的唯一ID生成器
 * 在这个应用中，我们需要生成appID, channelID，orderID等
 * 更新订单号生成方式：http://www.uustory.com/?p=2146
 */
public class  IDGenerator {

    private static IDGenerator instance;

    //private AtomicInteger currAppID;
    //private AtomicInteger currMasterID;
    //private AtomicInteger currChannelID;

    private int serverID = 0;
    private long currOrderSequence = 0L;
    private long lastTimeStamp = -1L;
    private long sequenceMask = (1<<22);

    private IDGenerator(){
        //loadCurrMaxID();
        serverID = Integer.parseInt(System.getProperty("serverid"));
    }

    public static IDGenerator getInstance(){
        if(instance == null){
            instance = new IDGenerator();
        }

        return instance;
    }

    public synchronized long nextOrderID(){
        Calendar can = Calendar.getInstance();
        int year = can.get(Calendar.YEAR) - 2013;
        int month = can.get(Calendar.MONTH) + 1;
        int day = can.get(Calendar.DAY_OF_MONTH);
        int hour = can.get(Calendar.HOUR_OF_DAY);
        int min = can.get(Calendar.MINUTE);
        int sec = can.get(Calendar.SECOND);

        long req = year;
        req = req << 4 | month;
        req = req << 5 | day;
        req = req << 5 | hour;
        req = req << 6 | min;
        req = req << 6 | sec;

        if(serverID >= 1024){
            Log.e("U8Server deploy_id must be in 0(include) and 1024(exclude)");
            return -1;
        }

        long currTime = req;
        if(req == lastTimeStamp){

            this.currOrderSequence = this.currOrderSequence + 1;
            if(this.currOrderSequence >= sequenceMask){
                this.currOrderSequence = sequenceMask;
                Log.e("WOW!!! u8server had generate more than %s orders per seconds. I'm sure you now have enough money to redevelop u8server to fix the problem", sequenceMask);
                return -1;
            }

        }else{
            this.currOrderSequence = 0L;
            lastTimeStamp = currTime;
        }

        req = req << 10| serverID;
        req = req << 22| this.currOrderSequence;

        return req;
    }

/*    private void loadCurrMaxID(){
        int maxID = 0;

        for(UGame game : CacheManager.getInstance().getGameList()){
            if(maxID < game.getAppID()){
                maxID = game.getAppID();
            }
        }

        this.currAppID = new AtomicInteger(maxID);

        maxID = 0;
        for(UChannelMaster master : CacheManager.getInstance().getMasterList()){
            if(maxID < master.getMasterID()){
                maxID = master.getMasterID();
            }
        }

        this.currMasterID = new AtomicInteger(maxID);

        maxID = 0;
        for(UChannel channel : CacheManager.getInstance().getChannelList()){
            if(maxID < channel.getChannelID()){
                maxID = channel.getChannelID();
            }
        }

        this.currChannelID = new AtomicInteger(maxID);
    }*/

    public int nextAppID(){
    	int maxID = 0;

        for(UGame game : CacheManager.getInstance().getGameList()){
            if(maxID < game.getAppID()){
                maxID = game.getAppID();
            }
        }
        
        return maxID + 1;
    }

    public int nextMasterID(){
    	int maxID = 0;
        for(UChannelMaster master : CacheManager.getInstance().getMasterList()){
            if(maxID < master.getMasterID()){
                maxID = master.getMasterID();
            }
        }
        
        return maxID + 1;
    }

    /**
     * 当前将渠道号，改为后台可以手动设置和修改的方式了
     * 不再自动生成。因为很多业务中，创建渠道或者后面修改渠道的时候，都有可能需要指定渠道号或者变更渠道号
     * @return
     
    @Deprecated
    public int nextChannelID(){
        return this.currChannelID.incrementAndGet();
    }
    */
}
