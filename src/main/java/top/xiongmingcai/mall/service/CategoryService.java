package top.xiongmingcai.mall.service;

import top.xiongmingcai.mall.model.pojo.Category;
import top.xiongmingcai.mall.model.request.CategoryReq;

public interface CategoryService {
    Category insertOneCategory(CategoryReq categoryReq);

    Category updateOneCategory(CategoryReq categoryReq);

    Category findOneCategory(Integer categoryId);

    Category deleteByPrimaryKey(Integer categoryId);
}
