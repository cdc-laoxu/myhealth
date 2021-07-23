package com.itxiaoxu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itxiaoxu.entity.PageResult;
import com.itxiaoxu.entity.QueryPageBean;
import com.itxiaoxu.service.CheckItemService;
import com.itxiaoxu.dao.CheckItemDao;
import com.itxiaoxu.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)//Dubbo的Service
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired//注入dao
    private CheckItemDao checkItemDao;

    @Override
    public void delete(Integer id) {
        //检查数据与检查组是否有关联
       Long count = checkItemDao.findCountByCheckItemID(id);
        System.out.println("count:"+count);
       if (count>0){
           //有关联，无法删除
           throw new RuntimeException();
       }
        checkItemDao.delete(id);
    }

    /**
     * 添加新增项实现方法
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    //条件查询
    public PageResult findByCondition(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        //分页助手
        PageHelper.startPage(currentPage,pageSize);
        //调用dao，返回Page对象
        Page<CheckItem> page = checkItemDao.findByCondition(queryString);

        //page转为PageResult
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();

        return new PageResult(total,rows);
    }

    @Override
    public CheckItem findById(Integer id) {
        CheckItem checkItem= checkItemDao.findById(id);
        return checkItem;
    }

    /**
     * 编辑检查项
     * @param checkItem
     */
    public void edit(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
