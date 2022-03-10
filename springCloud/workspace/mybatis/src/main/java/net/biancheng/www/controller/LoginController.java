package net.biancheng.www.controller;
import lombok.extern.slf4j.Slf4j;
import net.biancheng.www.bean.User;
import net.biancheng.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.util.Map;
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
}