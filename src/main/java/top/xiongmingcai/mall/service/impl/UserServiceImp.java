package top.xiongmingcai.mall.service.impl;

import org.springframework.stereotype.Service;
import top.xiongmingcai.mall.model.dao.UserMapper;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.service.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImp implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }
}

