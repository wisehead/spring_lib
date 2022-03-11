package se.upotential.www.mapper;
import se.upotential.www.bean.User;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface UserMapper {
    //通过用户名密码查询用户数据
    User getByUserNameAndPassword(User user);
}
