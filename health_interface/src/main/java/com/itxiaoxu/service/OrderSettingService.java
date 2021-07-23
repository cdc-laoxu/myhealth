package com.itxiaoxu.service;

import com.itxiaoxu.entity.PageResult;
import com.itxiaoxu.entity.QueryPageBean;
import com.itxiaoxu.pojo.OrderSetting;
import com.itxiaoxu.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

   public void add(List<OrderSetting> data);

   List<Map> findAllOrderByDate(String date);

   void update(OrderSetting orderSetting);
}
