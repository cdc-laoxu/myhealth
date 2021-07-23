package com.itxiaoxu.service;

import com.itxiaoxu.entity.PageResult;
import com.itxiaoxu.entity.QueryPageBean;
import com.itxiaoxu.entity.Result;
import com.itxiaoxu.pojo.CheckItem;
import com.itxiaoxu.pojo.Setmeal;


import java.util.List;
import java.util.Map;

public interface SetmealService {

   public void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    List<Map<String, Object>> findSetmealCount();
}
