package top.xiongmingcai.mall.model.dao;

import top.xiongmingcai.mall.model.pojo.User;

//@Mapper //在接口类上添加了@Mapper，在编译之后会生成相应的接口实现类
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}