package com.itxiaoxu.dao;

import com.itxiaoxu.pojo.Role;
import com.itxiaoxu.pojo.User;

import java.util.Set;

public interface RoleDao {
   public Set<Role> findByUserId(int userId);
}
