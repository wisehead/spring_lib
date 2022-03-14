package se.upotential.www.utils;

import java.util.UUID;
/**
 * @Description:使用UUIDUtils生产发送给用户的邮箱验证码
 */
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}