package com.itxiaoxu.dao;

import com.github.pagehelper.Page;
import com.itxiaoxu.pojo.OrderSetting;
import com.itxiaoxu.pojo.Setmeal;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {

   void add(OrderSetting orderSetting);
   Long findNumberByDate(Date date);
   void editNumberByDate(OrderSetting orderSetting);
   List<OrderSetting> findAllOrderByDate(Map map);
   OrderSetting findByDate(Date date);

   void editReservationsByDate(OrderSetting orderSetting);
}
