package com.itxiaoxu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itxiaoxu.constant.MessageConstant;
import com.itxiaoxu.entity.Result;
import com.itxiaoxu.pojo.Order;
import com.itxiaoxu.pojo.Setmeal;
import com.itxiaoxu.service.OrderService;
import com.itxiaoxu.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

   @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
       String validateCode = (String) map.get("validateCode");
       if (validateCode !=null &&validateCode.equals("1234")){
           Result result=null;
           //验证成功
           map.put("orderType", Order.ORDERTYPE_WEIXIN);//设置预约类型，分为微信预约、电话预约
           try {
               result = orderService.add(map);
               return result;
           } catch (Exception e) {
               e.printStackTrace();
               //预约失败
               return result;
           }

       }else {
           //验证码出错
           return new Result(false,MessageConstant.VALIDATECODE_ERROR);
       }

   }
}
