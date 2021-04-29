package top.xiongmingcai.mall.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.xiongmingcai.mall.exception.BussinessException;
import top.xiongmingcai.mall.exception.ExceptionEnum;
import top.xiongmingcai.mall.model.dao.CategoryMapper;
import top.xiongmingcai.mall.model.pojo.Category;
import top.xiongmingcai.mall.model.request.CategoryReq;
import top.xiongmingcai.mall.service.CategoryService;

import javax.annotation.Resource;

@Service
public class CategoryServiceImp implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Category insertOneCategory(CategoryReq categoryReq) {
        //查询是否存在
        Category category = categoryMapper.selectByName(categoryReq.getName());
        if (category != null) {
            throw new BussinessException(ExceptionEnum.NEED__CATEGORY_ALREADY_EXISTS);
        }


        Category category1 = new Category();
        BeanUtils.copyProperties(categoryReq, category1);

        int count = categoryMapper.insertCategory(category1);
        if (count == 0) {
            throw new BussinessException(ExceptionEnum.USER_CREATION_CATEGORY_FAILED);
        }
        return categoryMapper.selectByPrimaryKey(category1.getId());
    }
}
