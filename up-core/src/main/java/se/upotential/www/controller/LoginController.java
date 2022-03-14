package se.upotential.www.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import se.upotential.www.bean.User;
import se.upotential.www.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Map;

//import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @RequestMapping("/user/login")
    public String doLogin(User user, Map<String, Object> map, HttpSession session) {
        //从数据库中查询用户信息
        User loginUser = userService.getByUserNameAndPassword(user);
        if (loginUser != null) {
            session.setAttribute("loginUser", loginUser);
            log.info("登陆成功，用户名：" + loginUser.getUserName());
            //防止重复提交使用重定向
            return "redirect:/main.html";
        } else {
            map.put("msg", "用户名或密码错误");
            log.error("登陆失败");
            return "login";
        }
    }
    
    @RequestMapping("/user/signup")
    public String doSignUp(User user, Map<String, Object> map, HttpSession session) {
        //从数据库中查询用户信息
        int ret = userService.addUser(user);
        if (ret != 0) {
            //session.setAttribute("loginUser", loginUser);
            log.info("注册成功，用户名：" + user.getUserName() + ", EMail:" + user.getEmail());
            //防止重复提交使用重定向
            return "redirect:/main.html";
        } else {
            map.put("msg", "用户名或密码错误");
            log.error("注册失败");
            return "login";
        }
    }
}
