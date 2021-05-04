package top.xiongmingcai.mall.service;

import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;
import top.xiongmingcai.mall.model.pojo.Product;
import top.xiongmingcai.mall.model.request.ProductListReq;


public interface ProductService {
    Product findOneProduct(Integer productId);

    Product createOneProduct(Product productReq);

    String uploadFile(MultipartFile file);

    Product update(Product updateProduct);

    void deleteOneProduct(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatis);

    PageInfo<Product> listForAdmin(Integer pageNum, Integer pageSize);

    PageInfo<Product> list(ProductListReq productListReq, Integer pageNum, Integer pageSise);
}
