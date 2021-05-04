package top.xiongmingcai.mall.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;
import top.xiongmingcai.mall.exception.BussinessException;
import top.xiongmingcai.mall.exception.ExceptionEnum;
import top.xiongmingcai.mall.model.dao.ProductMapper;
import top.xiongmingcai.mall.model.pojo.Product;
import top.xiongmingcai.mall.service.ProductService;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceimp implements ProductService {
    public String FILE_UPLOAD_DIR = "/Users/xmc/IdeaProjects/mall.xiongmingcai.top/src/main/resources/static";
    @Resource
    private ProductMapper productMapper;

    @Override
    public Product findOneProduct(Integer productId) {
        return productMapper.selectByPrimaryKey(productId);
    }

    @Override
    public Product createOneProduct(Product productReq) {
        Product product = productMapper.selectByName(productReq.getName());
        if (product != null) {
            throw new BussinessException(ExceptionEnum.NEED__PRODUCT_ALREADY_EXISTS);
        }

        int count = productMapper.insertSelective(productReq);
        if (count != 1) {
            throw new BussinessException(ExceptionEnum.NEED___LOST_GOODS_IN_STORAGE);
        }
        return productMapper.selectByPrimaryKey(productReq.getId());
    }

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BussinessException(ExceptionEnum.PICTURE_SAVE_DOES_NOT_EXIST);
        }

        String originalFilename = file.getOriginalFilename();
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成文件名称UUID
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString() + suffixName;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String format = sdf.format(new Date());
        //创建文件
        FILE_UPLOAD_DIR = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        File fileDirectory = new File(FILE_UPLOAD_DIR);

        if (!fileDirectory.exists()) {

            if (fileDirectory.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
                throw new BussinessException(ExceptionEnum.USER_PICTURE_ROAD_KING_DOES_NOT_EXIST);
            }
        }
        File imageFile = new File(FILE_UPLOAD_DIR + "/", newFileName);
        try {
            file.transferTo(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BussinessException(ExceptionEnum.PICTURE_SAVE_DOES_NOT_EXIST);
        }

        return newFileName;

    }

    @Override
    public Product update(Product updateProduct) {
        if (updateProduct.getName() != null) {
            Product product = productMapper.selectByName(updateProduct.getName());
            if (product != null) {
                throw new BussinessException(ExceptionEnum.NEED__PRODUCT_ALREADY_EXISTS);
            }
        }
        int count = productMapper.updateByPrimaryKeySelective(updateProduct);
        if (count != 1) {
            throw new BussinessException(ExceptionEnum.NEED___LOST_GOODS_IN_STORAGE);
        }
        return productMapper.selectByPrimaryKey(updateProduct.getId());
    }

    @Override
    public void deleteOneProduct(Integer id) {
        int count = productMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            throw new BussinessException(ExceptionEnum.NEED___LOST_GOODS_IN_STORAGE);
        }
    }

    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatis) {
        productMapper.batchUpdateSellStatus(ids, sellStatis);
    }


}
