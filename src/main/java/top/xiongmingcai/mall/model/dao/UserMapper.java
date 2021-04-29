package top.xiongmingcai.mall.model.dao;

import org.apache.ibatis.annotations.Param;
import top.xiongmingcai.mall.model.pojo.User;

//@Mapper //在接口类上添加了@Mapper，在编译之后会生成相应的接口实现类
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUsername(String username);

    User selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}