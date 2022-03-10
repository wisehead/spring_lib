package net.biancheng.www.service;
import net.biancheng.www.bean.User;
public interface UserService {
    public User getByUserNameAndPassword(User user);
}