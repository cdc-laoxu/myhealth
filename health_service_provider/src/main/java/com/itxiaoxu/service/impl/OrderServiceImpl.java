package com.itxiaoxu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itxiaoxu.constant.MessageConstant;
import com.itxiaoxu.dao.MemberDao;
import com.itxiaoxu.dao.OrderDao;
import com.itxiaoxu.dao.OrderSettingDao;
import com.itxiaoxu.dao.SetmealDao;
import com.itxiaoxu.entity.PageResult;
import com.itxiaoxu.entity.QueryPageBean;
import com.itxiaoxu.entity.Result;
import com.itxiaoxu.pojo.Member;
import com.itxiaoxu.pojo.Order;
import com.itxiaoxu.pojo.OrderSetting;
import com.itxiaoxu.pojo.Setmeal;
import com.itxiaoxu.service.OrderService;
import com.itxiaoxu.service.SetmealService;
import com.itxiaoxu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)//Dubbo的Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Result add(Map map) throws Exception {
       String telephone = (String) map.get("telephone");
       String s_date = (String) map.get("orderDate");
       Date orderDate = DateUtils.parseString2Date(s_date);
       //1.检查当天是否有预约设置，根据日期查询ordersetting表
        OrderSetting orderSetting = orderSettingDao.findByDate(orderDate);
        if (orderSetting == null){
           return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //2.判断是否满员
        int reservations = orderSetting.getReservations();
        int number = orderSetting.getNumber();
        if (reservations>=number){
           return new  Result(false,MessageConstant.ORDER_FULL);
        }
        //3.判断是否重复预约（同一天同一个套餐）
            //根据身份证号码查找member
        String idCard = (String) map.get("idCard");
        Member member = memberDao.findByIdCard(idCard);
        //根据memberid,orderDate,setmeal_id 同时查找t_order表
        if (member != null){
            Integer memberId = member.getId();
            String setmealId = (String) map.get("setmealId");
            Order order = new Order(memberId,orderDate,Integer.parseInt(setmealId));
            List<Order> byCondition = orderDao.findByCondition(order);
            if (byCondition != null){
                //重复预约
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else {
            //新用户，注册会员并且完成预约
           member = new Member();
            String name = (String) map.get("name");
            member.setName(name);
            String sex = (String) map.get("sex");
            member.setSex(sex);
            member.setIdCard(idCard);
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberDao.add(member);
        }
        //可以预约，预约人数+1
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByDate(orderSetting);
        //最后保存信息到order表
        Order order= new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(orderDate);
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType(Order.ORDERTYPE_WEIXIN);
        String setmealId = (String) map.get("setmealId");
        order.setSetmealId(Integer.parseInt(setmealId));
        orderDao.add(order);

        System.out.println(order.getId());
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());

    }
}
