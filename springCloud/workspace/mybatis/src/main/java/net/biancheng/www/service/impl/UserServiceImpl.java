package net.biancheng.www.service.impl;
import net.biancheng.www.bean.User;
import net.biancheng.www.mapper.UserMapper;
import net.biancheng.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User getByUserNameAndPassword(User user) {
        User loginUser = userMapper.getByUserNameAndPassword(user);
        return loginUser;
    }
}