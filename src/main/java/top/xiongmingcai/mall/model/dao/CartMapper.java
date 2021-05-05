package top.xiongmingcai.mall.model.dao;

import org.apache.ibatis.annotations.Param;
import top.xiongmingcai.mall.model.pojo.Cart;
import top.xiongmingcai.mall.model.vo.CartVo;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectByUserIdAndProductId(@Param("userId") Integer userID, @Param("productId") Integer productId);

    List<CartVo> list(@Param("userId") Integer userId);
}