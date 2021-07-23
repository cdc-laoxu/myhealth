package com.itxiaoxu.dao;

import com.itxiaoxu.pojo.Permission;
import com.itxiaoxu.pojo.Role;

import java.util.Set;

public interface PermissionDao {
   public Set<Permission> findByRoleId(int roleId);
}
