package com.itxiaoxu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itxiaoxu.dao.CheckGroupDao;
import com.itxiaoxu.entity.PageResult;
import com.itxiaoxu.entity.QueryPageBean;
import com.itxiaoxu.pojo.CheckGroup;
import com.itxiaoxu.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)//Dubbo的Service
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    /**
     * 新增检查组
     */
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.插入检查组基础数据
        checkGroupDao.addCheckGroup(checkGroup);

        //2.映射文件中做返回自增id并赋值，这里取出插入后的id
        //数据存入map，调用dao关联表
        Map<String,Integer> map = new HashMap<String,Integer>();
        for (Integer checkitemId : checkitemIds) {
            Integer checkGroupId = checkGroup.getId();
            map.put("checkGroupId",checkGroupId);
            map.put("checkitemId",checkitemId);
            checkGroupDao.addCheckGroupAndCheckItem(map);
        }
    }

    @Override
    public PageResult queryPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckGroup> checkGroups = checkGroupDao.qureyPage(queryPageBean.getQueryString());

        return new PageResult(checkGroups.getTotal(),checkGroups.getResult());
    }

    @Override
    public CheckGroup findByID(Integer id) {
        CheckGroup checkGroup = checkGroupDao.findById(id);
        return checkGroup;
    }

    @Override
    public List<Integer> findCheckGroupAndCheckItem(Integer CheckGroupId) {
        List<Integer> checkItems = checkGroupDao.findCheckGroupAndCheckItem(CheckGroupId);
        return checkItems;
    }

    /**
     * 修改检查组内容
     * @param checkGroup
     * @param checkitemIds
     */
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {

        //3.更新checkGroup基本数据
        checkGroupDao.update(checkGroup);

        //1.删除中间表旧的checkitem
        if (checkitemIds!=null && checkitemIds.length>0){
            checkGroupDao.deleteCheckGroupAndCheckItem(checkGroup.getId());
        }
        //2.插入中间表新的checkitem
        Integer checkGroupId = checkGroup.getId();

        for (Integer checkitemId : checkitemIds) {
            Map<String,Integer> map = new HashMap<String,Integer>();
            map.put("checkGroupId",checkGroupId);
            map.put("checkitemId",checkitemId);
            checkGroupDao.insertCheckItem(map);
        }


    }

    //删除检查组
    public void delete(Integer id) {
        //1.根据checkgroup的id清空中间表
        checkGroupDao.deleteCheckGroupAndCheckItem(id);
        //2.删除checkgroup
        checkGroupDao.delete(id);
    }

    /**
     * 查询所有
     * @return
     */
    public List<CheckGroup> findAll() {
       List<CheckGroup> list = checkGroupDao.findAll();
        return list;
    }


}
