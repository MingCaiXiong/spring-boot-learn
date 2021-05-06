package top.xiongmingcai.mall.model.dao;

import org.apache.ibatis.annotations.Param;
import top.xiongmingcai.mall.model.pojo.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    Order selectByOrderNo(@Param("orderNo") String orderNo);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}