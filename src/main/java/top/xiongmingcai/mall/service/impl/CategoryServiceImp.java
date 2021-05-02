package top.xiongmingcai.mall.service.impl;

import org.springframework.stereotype.Service;
import top.xiongmingcai.mall.exception.BussinessException;
import top.xiongmingcai.mall.exception.ExceptionEnum;
import top.xiongmingcai.mall.model.dao.CategoryMapper;
import top.xiongmingcai.mall.model.pojo.Category;
import top.xiongmingcai.mall.model.request.CategoryReq;
import top.xiongmingcai.mall.service.CategoryService;

import javax.annotation.Resource;
import java.util.Date;

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
        category1.setName(categoryReq.getName());
        category1.setType(categoryReq.getType());
        category1.setParentId(categoryReq.getParentId());
        category1.setOrderNum(categoryReq.getOrderNum());
        category1.setUpdateTime(new Date());
        category1.setCreateTime(new Date());

        int count = categoryMapper.insertCategory(category1);
        if (count == 0) {
            throw new BussinessException(ExceptionEnum.USER_CREATION_CATEGORY_FAILED);
        }
        return categoryMapper.selectByPrimaryKey(category1.getId());
    }

    @Override
    public Category updateOneCategory(CategoryReq categoryReq) {
        if (categoryReq.getId() == null) {
            throw new BussinessException(ExceptionEnum.NEED_MISSING_PARAMETERS_ID);
        }

        //查询分类是否存在重名
        Category categoryDuplicateName = categoryMapper.selectByName(categoryReq.getName());

        if (categoryDuplicateName != null) {
            throw new BussinessException(ExceptionEnum.NEED__CATEGORY_ALREADY_EXISTS);
        }
        //查询传入ID是否存在数据
        Category category = categoryMapper.selectByPrimaryKey(categoryReq.getId());
        if (category == null) {
            throw new BussinessException(ExceptionEnum.NEED_CATEGORY__DOES_NOT_EXIST);

        } else {
            category.setName(categoryReq.getName());
            category.setType(categoryReq.getType());
            category.setParentId(categoryReq.getParentId());
            category.setOrderNum(categoryReq.getOrderNum());
            category.setUpdateTime(new Date());
//            BeanUtils.copyProperties(categoryReq, category);
            int count = categoryMapper.updateByPrimaryKeySelective(category);
            if (count == 0 || count > 1) {
                throw new BussinessException(ExceptionEnum.USER_CREATION_CATEGORY_FAILED);
            }
            return category;
        }
    }

    @Override
    public Category findOneCategory(Integer categoryId) {
        return categoryMapper.selectByPrimaryKey(categoryId);
    }
}
