package top.xiongmingcai.mall.model.dao;

import org.apache.ibatis.annotations.Mapper;
import top.xiongmingcai.mall.model.pojo.OrderItem;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    List<OrderItem> selectByOrderNo(String orderNo);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}