package top.xiongmingcai.mall.controller;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xiongmingcai.mall.common.ApiRestResponse;
import top.xiongmingcai.mall.exception.ExceptionEnum;
import top.xiongmingcai.mall.model.pojo.Product;
import top.xiongmingcai.mall.model.request.ProductReq;
import top.xiongmingcai.mall.model.request.updateProductReq;
import top.xiongmingcai.mall.service.ProductService;
import top.xiongmingcai.mall.util.NullAwareBeanUtilsBean;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Date;

@RestControllerAdvice
@RequestMapping("/admin")
public class ProductController {
    @Resource
    private ProductService productService;
    private BeanUtilsBean notNull = new NullAwareBeanUtilsBean();

    @GetMapping(value = "/product/{productId}")
    public ApiRestResponse getProduct(@PathVariable(value = "productId") Integer productId) {
        Product oneProduct = productService.findOneProduct(productId);
        return ApiRestResponse.success(oneProduct);
    }

    @PostMapping("/product")
    public ApiRestResponse postProduct(@Valid ProductReq productReq) {
        Product product = new Product();
        BeanUtils.copyProperties(productReq, product);
        Product oneProduct = productService.createOneProduct(product);
        return ApiRestResponse.success(oneProduct);
    }

    @RequestMapping(value = "/upload/image", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ApiRestResponse postUploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws UnsupportedEncodingException {
        String uploadFile = productService.uploadFile(file);
        //协议+ip地址+端口号
        String format = MessageFormat.format("{0}://{1}:{2}/static/{3}", request.getScheme(), request.getServerName(), String.valueOf(request.getServerPort()), uploadFile);
        return ApiRestResponse.success(format);
    }

    @PostMapping("/product/{id}")
    public ApiRestResponse updateProduct(
            @PathVariable(value = "id") Integer productId,
            @Valid updateProductReq productReq) throws InvocationTargetException, IllegalAccessException {
        Product sourcePriduct = productService.findOneProduct(productId);

        if (sourcePriduct == null) {
            return ApiRestResponse.error(ExceptionEnum.NEED_MISSING_PARAMETERS_ID.getCode(), "缺少商品ID");
        }

        sourcePriduct.setId(productId);

        notNull.copyProperties(sourcePriduct, productReq);
        if (productReq.getName() == null) {
            sourcePriduct.setName(null);
        }
        sourcePriduct.setCreateTime(new Date());
        System.out.println("oneProduct1 = " + sourcePriduct);
        final Product updatedoneProduct = productService.update(sourcePriduct);
        return ApiRestResponse.success(updatedoneProduct);
    }

    @DeleteMapping("/product")
    public ApiRestResponse deleteOneProduct(Integer id) {
        productService.deleteOneProduct(id);
        return ApiRestResponse.success();
    }
}
