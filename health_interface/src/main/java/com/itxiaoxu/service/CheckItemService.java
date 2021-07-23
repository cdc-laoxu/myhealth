package com.itxiaoxu.service;

import com.github.pagehelper.Page;
import com.itxiaoxu.entity.PageResult;
import com.itxiaoxu.entity.QueryPageBean;
import com.itxiaoxu.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {

    /**
     * 删除检查项
     * @param id
     */
    void delete(Integer id);

    /**
     * 增加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询方法
     * @param queryPageBean
     * @return
     */
    PageResult findByCondition(QueryPageBean queryPageBean);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();
}
