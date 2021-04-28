package top.xiongmingcai.mall.service;

import top.xiongmingcai.mall.model.pojo.User;

public interface UserService {
    User getUser();

    User register(String username, String password);
}