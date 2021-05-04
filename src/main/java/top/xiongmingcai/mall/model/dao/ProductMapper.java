package top.xiongmingcai.mall.model.dao;

import org.apache.ibatis.annotations.Param;
import top.xiongmingcai.mall.model.pojo.Product;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    Product selectByName(String name);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    int batchUpdateSellStatus(@Param("ids") Integer[] ids, @Param("sellStatus") Integer sellStatus);

    List<Product> selectListForAdmin();
}