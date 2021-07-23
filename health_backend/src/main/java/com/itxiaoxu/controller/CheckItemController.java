package com.itxiaoxu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itxiaoxu.entity.PageResult;
import com.itxiaoxu.entity.QueryPageBean;
import com.itxiaoxu.service.CheckItemService;
import com.itxiaoxu.constant.MessageConstant;
import com.itxiaoxu.entity.Result;
import com.itxiaoxu.pojo.CheckItem;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference//查找服务
    private CheckItemService checkItemService;

    //新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try{
            checkItemService.add(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean QueryPageBean){
        PageResult pageResult = checkItemService.findByCondition(QueryPageBean);
        return pageResult;
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    public Result delete(Integer id){

        try {
            checkItemService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }

        return  new Result(false,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        CheckItem checkItem = null;
        try {
            checkItem = checkItemService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return   new Result(true,MessageConstant.QUERY_CHECKGROUP_FAIL,checkItem);
        }
        return   new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItem);
    }
    @PostMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return   new Result(true,MessageConstant.EDIT_CHECKITEM_FAIL,checkItem);
        }
        return   new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS,checkItem);
    }

    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckItem> list = null;
        try {
            list = checkItemService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_FAIL,list);
        }
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }
}
