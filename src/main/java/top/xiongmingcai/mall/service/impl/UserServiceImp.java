package top.xiongmingcai.mall.service.impl;

import org.springframework.stereotype.Service;
import top.xiongmingcai.mall.common.Constant;
import top.xiongmingcai.mall.exception.BussinessException;
import top.xiongmingcai.mall.exception.ExceptionEnum;
import top.xiongmingcai.mall.model.dao.UserMapper;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.service.UserService;
import top.xiongmingcai.mall.util.MD5Utils;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class UserServiceImp implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }

    @Override
    public User register(String username, String password) {
        //查询用户名是否存在,不允许重名
        User result = userMapper.selectByUsername(username);
        if (result != null) {
            throw new BussinessException(ExceptionEnum.NEED_DO_NOT_ALLOW_DUPLICATE_NAMES);
        }

        User user = new User();
        try {

            user.setUsername(username);
            user.setPassword(MD5Utils.getMD5Str(password + Constant.SALT));
            user.setCreateTime(new Date());
            int count = userMapper.insertSelective(user);
            if (count == 0) {
                throw new BussinessException(ExceptionEnum.USER_CREATION_FAILED);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return user;
    }
}

