package top.xiongmingcai.mall.model.dao;

import top.xiongmingcai.mall.model.pojo.Category;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    Category selectByName(String name);

    int insertCategory(Category category);

    List<Category> selectList(Integer pageNum, Integer pageSize);
}