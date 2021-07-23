package com.itxiaoxu.service;

import com.itxiaoxu.entity.PageResult;
import com.itxiaoxu.entity.QueryPageBean;
import com.itxiaoxu.pojo.CheckGroup;
import com.itxiaoxu.pojo.CheckItem;

import java.util.List;

public interface CheckGroupService {
    public void add(CheckGroup checkGroup,Integer[] checkitemIds);

    PageResult queryPage(QueryPageBean queryPageBean);

    CheckGroup findByID(Integer id);

     List<Integer> findCheckGroupAndCheckItem(Integer CheckGroupId);

    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    void delete(Integer id);

    List<CheckGroup> findAll();

}
