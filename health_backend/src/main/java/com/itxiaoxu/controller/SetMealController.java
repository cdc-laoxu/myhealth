package com.itxiaoxu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itxiaoxu.constant.MessageConstant;
import com.itxiaoxu.constant.RedisConstant;
import com.itxiaoxu.entity.PageResult;
import com.itxiaoxu.entity.QueryPageBean;
import com.itxiaoxu.entity.Result;
import com.itxiaoxu.pojo.CheckGroup;
import com.itxiaoxu.pojo.Setmeal;
import com.itxiaoxu.service.CheckGroupService;
import com.itxiaoxu.service.SetmealService;
import com.itxiaoxu.utils.QiniuUtils;
import com.itxiaoxu.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import javax.ws.rs.POST;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference//查找服务
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/upload")
    public Result findAll(MultipartFile imgFile){

        String originalFilename = imgFile.getOriginalFilename();
        int lastIndexOf = originalFilename.lastIndexOf('.');
        String extention = originalFilename.substring(lastIndexOf);//获取文件后缀
        String fileName = UUID.randomUUID().toString() + extention;


        try {
            //成功，返回图片名称
            //redis缓存上传的图片名称
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
        }


        @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
            try {
                setmealService.add(setmeal,checkgroupIds);
                //redis缓存保存的图片名称
                jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);

            }
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        }
        @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
            return setmealService.findPage(queryPageBean);
        }


}
