package com.itxiaoxu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itxiaoxu.constant.MessageConstant;
import com.itxiaoxu.constant.RedisConstant;
import com.itxiaoxu.entity.PageResult;
import com.itxiaoxu.entity.QueryPageBean;
import com.itxiaoxu.entity.Result;
import com.itxiaoxu.pojo.Setmeal;
import com.itxiaoxu.service.SetmealService;
import com.itxiaoxu.utils.QiniuUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference//查找服务
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

   @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
       try {
           List<Setmeal> list =  setmealService.findAll();
           return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,list);
       } catch (Exception e) {
           e.printStackTrace();
           return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);

       }
   }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal =  setmealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);

        }
    }
}
