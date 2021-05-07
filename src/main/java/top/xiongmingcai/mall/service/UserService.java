package top.xiongmingcai.mall.service;

import top.xiongmingcai.mall.model.pojo.User;

public interface UserService {

    User register(String username, String password);

    User login(String username, String password);

    User updatePersonalizedSignatureByUser(User user);

    boolean checkPermissions(User user);
}