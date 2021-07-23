package com.itxiaoxu.jobs;

import com.itxiaoxu.constant.RedisConstant;
import com.itxiaoxu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set != null) {
            for (String fileName : set) {
                //根据文件名称删除七牛云服务器的文件
                QiniuUtils.deleteFileFromQiniu(fileName);
                //删除redis中的垃圾文件名称
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            }
        }
    }

    public void clearAllImg() {
        //清除大集合
        Set<String> bigSet = jedisPool.getResource().smembers(RedisConstant.SETMEAL_PIC_RESOURCES);
        if (bigSet != null) {
            for (String picName1 : bigSet) {
                //1.先清除服务器上的数据
                QiniuUtils.deleteFileFromQiniu(picName1);
                //2.再清理redis上的数据
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, picName1);
            }
            //清除小集合
            Set<String> smallSet = jedisPool.getResource().smembers(RedisConstant.SETMEAL_PIC_DB_RESOURCES);
            if (smallSet != null) {
                for (String picName2 : smallSet) {
                    //1.先清除服务器上的数据
                    QiniuUtils.deleteFileFromQiniu(picName2);
                    //2.再清理redis上的数据
                    jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES, picName2);
                }
            }
        }
    }
}