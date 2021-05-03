package top.xiongmingcai.mall.service;

import org.springframework.web.multipart.MultipartFile;
import top.xiongmingcai.mall.model.pojo.Product;
import top.xiongmingcai.mall.model.request.ProductReq;


public interface ProductService {
    Product findOneProduct(Integer productId);

    Product createOneProduct(ProductReq productReq);

    String uploadFile(MultipartFile file);
}
