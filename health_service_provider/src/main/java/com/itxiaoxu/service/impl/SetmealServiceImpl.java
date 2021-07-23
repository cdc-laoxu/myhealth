package com.itxiaoxu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itxiaoxu.dao.CheckGroupDao;
import com.itxiaoxu.dao.SetmealDao;
import com.itxiaoxu.entity.PageResult;
import com.itxiaoxu.entity.QueryPageBean;
import com.itxiaoxu.pojo.CheckGroup;
import com.itxiaoxu.pojo.Setmeal;
import com.itxiaoxu.service.CheckGroupService;
import com.itxiaoxu.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)//Dubbo的Service
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //新曾基础信息,给setmeal赋值ID
        setmealDao.addSetmeal(setmeal);

        Integer setmealId = setmeal.getId();

        //关联中间表表
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("setmealId", setmealId);
                map.put("checkgroupId", checkgroupId);
                setmealDao.addSetmealAndCheckGroup(map);
            }
        }

    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    public PageResult findPage(QueryPageBean queryPageBean) {
        //运用分页助手，进行分页查询
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
         PageHelper.startPage(currentPage, pageSize);
        //条件查询
        String queryString = queryPageBean.getQueryString();
        Page<Setmeal> page = setmealDao.querySetmealByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        List<Setmeal>  list = setmealDao.findAll();

        return list;
    }

    @Override
    public Setmeal findById(Integer id) {
       Setmeal setmeal = setmealDao.findById(id);
        return setmeal;
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        List<Map<String, Object>> setmealCount = setmealDao.findSetmealCount();

        return setmealCount;
    }
}
