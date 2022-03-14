package se.upotential.www.service.impl;
import se.upotential.www.bean.User;
import se.upotential.www.mapper.UserMapper;
import se.upotential.www.service.UserService;
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
    @Override
    public int addUser(User user) {
        int ret = userMapper.addUser(user);
        String token = user.getToken();
        System.out.println("token:"+token);
        String subject = "Activation mail from UPotential.";
        ///checkCode?code=xxx即是我们点击邮件链接之后进行更改状态的
        String context = "<h1>此邮件为官方激活邮件！请点击下面链接完成激活操作！</h1> <a href=\"http://localhost:8001/selectCode?token="+token+"\">激活请点击:"+token+"</a> ";
        //发送激活邮件
        mailService.sendHtmlMail (user.getEmail(),subject,context);
        return ret;
    }
}
