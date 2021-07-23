package com.itxiaoxu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itxiaoxu.dao.OrderSettingDao;
import com.itxiaoxu.dao.SetmealDao;
import com.itxiaoxu.entity.PageResult;
import com.itxiaoxu.entity.QueryPageBean;
import com.itxiaoxu.pojo.OrderSetting;
import com.itxiaoxu.pojo.Setmeal;
import com.itxiaoxu.service.OrderSettingService;
import com.itxiaoxu.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)//Dubbo的Service
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 新增预约设置
     *
     * @param data
     */
    public void add(List<OrderSetting> data) {
        //遍历集合
        if (data != null && data.size() > 0) {
            for (OrderSetting orderSetting : data) {
                Long count = orderSettingDao.findNumberByDate(orderSetting.getOrderDate());
                if (count>0){
                    //数据库已有当天的预约数据，下一步为更新操作
                    orderSettingDao.editNumberByDate(orderSetting);
                }else {
                    //数据库没有当天数据，执行插入操作
                    orderSettingDao.add(orderSetting);
                }

            }
        }
    }

    @Override
    public List<Map> findAllOrderByDate(String date) {
        //每个月的起始日期
        String begin = date+"-1";
        String end = date+"-31";
        Map<String,String> map = new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        List<OrderSetting> list = orderSettingDao.findAllOrderByDate(map);
        //把 List<OrderSetting>转为List<Map>
        List<Map> maps = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map<String,Object> m = new HashMap<>();
            int day = orderSetting.getOrderDate().getDate();
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();
            m.put("date",day);
            m.put("number",number);
            m.put("reservations",reservations);
            maps.add(m);
        }
        return maps;
    }

    /**
     * 设置预约人数
     * @param orderSetting
     */
    public void update(OrderSetting orderSetting) {
        //查找数据库，有没有当天的数据
        if (orderSetting !=null) {
            Long count = orderSettingDao.findNumberByDate(orderSetting.getOrderDate());
            if (count>0){
                //数据库有数据，更新数据
                orderSettingDao.editNumberByDate(orderSetting);
            }else {
                //数据库没有数据，插入数据
                orderSettingDao.add(orderSetting);
            }
        }
    }

}
