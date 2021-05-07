package top.xiongmingcai.mall.model.dao;

import org.apache.ibatis.annotations.Mapper;
import top.xiongmingcai.mall.model.pojo.Category;
import top.xiongmingcai.mall.model.vo.CategoryVo;

import java.util.List;

@Mapper
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

    List<CategoryVo> selectByParentId(Integer parentId);
}