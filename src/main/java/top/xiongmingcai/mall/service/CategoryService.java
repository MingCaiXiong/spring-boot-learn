package top.xiongmingcai.mall.service;

import top.xiongmingcai.mall.model.pojo.Category;
import top.xiongmingcai.mall.model.request.CategoryReq;

public interface CategoryService {
    Category insertOneCategory(CategoryReq categoryReq);
}
