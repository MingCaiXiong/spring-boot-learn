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

    /**
     * @param username
     * @param password
     * @return 登陆成功的用户
     */
    @Override
    public User login(String username, String password) {
        String md5Str = null;
        try {
            md5Str = MD5Utils.getMD5Str(password + Constant.SALT);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectByUsername(username);

        if (!user.getPassword().equals(md5Str)) {
            throw new BussinessException(ExceptionEnum.USER_WRONG_PASSWORD);
        }
        user.setPassword(null);
        return user;
    }

    @Override
    public User updatePersonalizedSignatureByUser(User user) {
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount != 1) {
            throw new BussinessException(ExceptionEnum.NEED_UPDATE_FAILED);
        }
        return user;
    }

    @Override
    public boolean checkPermissions(User user) {
        return user.getRole().equals(2);
    }
}

