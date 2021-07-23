package com.itxiaoxu.dao;

import com.github.pagehelper.Page;
import com.itxiaoxu.entity.QueryPageBean;
import com.itxiaoxu.pojo.CheckGroup;
import com.itxiaoxu.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {

    public void addSetmeal(Setmeal setmeal);

    void addSetmealAndCheckGroup(Map<String, Integer> map);

    Page<Setmeal> querySetmealByCondition(String queryString);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);
    List<Map<String,Object>> findSetmealCount();
}
