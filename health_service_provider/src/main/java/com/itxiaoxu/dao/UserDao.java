package com.itxiaoxu.dao;

import com.itxiaoxu.pojo.Order;
import com.itxiaoxu.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
   public User findByUserName(String username);
}
