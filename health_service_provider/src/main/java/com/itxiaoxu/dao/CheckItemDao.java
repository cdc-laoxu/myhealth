package com.itxiaoxu.dao;

import com.github.pagehelper.Page;
import com.itxiaoxu.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

    void add(CheckItem checkItem);

    Page<CheckItem> findByCondition(String queryString);

    void delete(Integer id);

    Long findCountByCheckItemID(Integer id);

    CheckItem findById(Integer id);

    /**
     * 编辑检查项
     * @param checkItem
     */
    void update(CheckItem checkItem);

    List<CheckItem> findAll();
}
