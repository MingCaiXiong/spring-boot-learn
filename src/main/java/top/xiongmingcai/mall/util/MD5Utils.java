package top.xiongmingcai.mall.util;

import org.springframework.util.DigestUtils;

import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static String getMD5Str(String password) throws NoSuchAlgorithmException {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
