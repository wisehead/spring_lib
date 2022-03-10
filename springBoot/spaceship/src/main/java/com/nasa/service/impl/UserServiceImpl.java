package com.nasa.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
//import com.nasa.dao.PermissionDao;
//import com.nasa.dao.RoleDao;
import com.nasa.dao.UserDao;
//import com.nasa.model.Permission;
//import com.nasa.model.Role;
import com.nasa.model.User;
import com.nasa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangbin on 2018/8/6.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    /*
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    */

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<User> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userDao.selectAll();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

    @Override
    public User validateUser(String username, String password) {
        return userDao.selectByUsernamePass(username, password);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        //return userDao.deleteByPrimaryKey(id);
    	return 0;
    }

    @Override
    public int insertSelective(User record) {
        //return userDao.insertSelective(record);
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        //return userDao.updateByPrimaryKeySelective(record);
    	return 0;
    }

    @Override
    public User selectByUsername(String username) {
        //return userDao.selectByUsername(username);
    	return null;
    }
/*
    @Override
    public User findRoleAndPermissions(User user) {
        List<Role> roleList = roleDao.selectRoleByUserId(user.getId());
        user.setRoleList(roleList);

        List<Permission> permissions = new ArrayList<>();
        for (Role role: roleList) {
            List<Permission> permissionlist = permissionDao.selectPermissionIdByRoleId(role.getId());
            role.setPermissionList(permissionlist);
            permissions.addAll(permissionlist);
        }
        user.setPermissionList(permissions);
        return user;
    }
    */

	@Override
	public User findRoleAndPermissions(User user) {
		// TODO Auto-generated method stub
		return null;
	}
}
