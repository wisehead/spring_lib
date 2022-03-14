package se.upotential.www.service;
import se.upotential.www.bean.User;
public interface UserService {
    public User getByUserNameAndPassword(User user);
    public int addUser(User user);
}
