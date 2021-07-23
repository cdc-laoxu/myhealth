package com.itxiaoxu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itxiaoxu.constant.MessageConstant;
import com.itxiaoxu.dao.*;
import com.itxiaoxu.entity.Result;
import com.itxiaoxu.pojo.*;
import com.itxiaoxu.service.OrderService;
import com.itxiaoxu.service.UserService;
import com.itxiaoxu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service(interfaceClass = UserService.class)//Dubbo的Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    /**
     * 根据用户名查找用户密码，拥有角色，权限
     *
     * @param username
     * @return
     */
    public User findByUserName(String username) {
        if (username == null && username.length() == 0) {
            return null;
        }
        User user = userDao.findByUserName(username);
        if (user == null) {
            return null;
        }
        Integer userId = user.getId();
        //根据userId查找roles
        Set<Role> roles = roleDao.findByUserId(userId);
        for (Role role : roles) {
            Integer roleId = role.getId();
            Set<Permission> permissions = permissionDao.findByRoleId(roleId);
            role.setPermissions(permissions);
        }
        user.setRoles(roles);
        return user;
    }
}
