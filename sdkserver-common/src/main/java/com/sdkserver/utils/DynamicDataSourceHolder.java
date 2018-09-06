package com.sdkserver.utils;

/**
 * 数据源选择类
 * 使用ThreadLocal技术来记录当前线程中的数据源的key
 *
 * Created by xcc on 2016/12/28.
 */

public class DynamicDataSourceHolder {
    //写库对应的数据源key
    public static final String MASTER = "master";

    //读库对应的数据源key，一主多从下已经没有意义
    public static final String SLAVE = "slave";

    //使用ThreadLocal记录当前线程的数据源key
    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    /**
     * 设置数据源key
     * @param key
     */
    public static void putDataSourceKey(String key) {
        holder.set(key);
    }

    /**
     * 获取数据源key
     * @return
     */
    public static String getDataSourceKey() {
        return holder.get();
    }

    /**
     * 目前是否是master
     * @return
     */
    public static boolean isMaster() {
        return holder.get().equals(MASTER);
    }

    /**
     * 标记写库
     */
    public static void markMaster(){
        putDataSourceKey(MASTER);
    }

    /**
     * 标记读库
     */
    public static void markSlave(){
        putDataSourceKey(SLAVE);
    }
}