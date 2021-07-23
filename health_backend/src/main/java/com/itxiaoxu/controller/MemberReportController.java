package com.itxiaoxu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itxiaoxu.constant.MessageConstant;
import com.itxiaoxu.entity.Result;
import com.itxiaoxu.service.ReportService;
import com.itxiaoxu.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class MemberReportController {
    @Reference
    private ReportService reportService;
    @Reference
    private SetmealService setmealService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        Map<String, Object> map = new HashMap<>();
        List<String> months = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);
        /*calendar.getTime();*/
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            String date = new SimpleDateFormat("yyyy.MM").format(calendar.getTime());
            months.add(date);
        }
        map.put("months", months);
        //x轴为以当前日往后推12个月，每个月起


        List<Integer> memberCount = reportService.findMemberCountByDate(months);

        map.put("memberCount", memberCount);
        //y轴为数据库每个月有多少个会员

        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        try {
            Map<String,Object> data = new HashMap<>();
            List<String> setmealName= new ArrayList<>();
            //先获取map，再遍历map拿到setmealName

            List<Map<String,Object>> setmealCount = setmealService.findSetmealCount();
            for (Map<String, Object> map : setmealCount) {
                String name = (String) map.get("name");
                setmealName.add(name);
            }
            data.put("setmealName",setmealName);
            data.put("setmealCount",setmealCount);
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,data);
        } catch (Exception e) {
            e.printStackTrace();
           return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
}