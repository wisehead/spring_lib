package com.nasa.service;

import com.github.pagehelper.PageInfo;
import com.nasa.model.User;

public interface UserService {
    User selectByPrimaryKey(Integer id);

    PageInfo<User> selectAll(int pageNum, int pageSize);

    User validateUser(String username, String password);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);

    User selectByUsername(String username);

    User findRoleAndPermissions(User user);
}