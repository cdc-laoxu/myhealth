package com.itxiaoxu.dao;

import com.github.pagehelper.Page;
import com.itxiaoxu.pojo.CheckGroup;
import com.itxiaoxu.pojo.CheckItem;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {

    public void addCheckGroup(CheckGroup checkGroup);

    void addCheckGroupAndCheckItem(Map<String, Integer> map);
    Page<CheckGroup> qureyPage(String queryString);

    CheckGroup findById(Integer id);
    List<Integer> findCheckGroupAndCheckItem(Integer CheckGroupId);

    void deleteCheckGroupAndCheckItem(Integer checkGroupId);

    void insertCheckItem(Map<String, Integer> map);

    void update(CheckGroup checkGroup);

    void delete(Integer id);

    List<CheckGroup> findAll();

    void insertChic();
}
