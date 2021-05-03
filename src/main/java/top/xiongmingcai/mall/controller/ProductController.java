package top.xiongmingcai.mall.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xiongmingcai.mall.common.ApiRestResponse;
import top.xiongmingcai.mall.model.pojo.Product;
import top.xiongmingcai.mall.model.request.ProductReq;
import top.xiongmingcai.mall.service.ProductService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

@RestControllerAdvice
@RequestMapping("/admin")
public class ProductController {
    @Resource
    private ProductService productService;

    @GetMapping(value = "/product/{productId}")
    public ApiRestResponse getProduct(@PathVariable(value = "productId") Integer productId) {
        Product oneProduct = productService.findOneProduct(productId);
        return ApiRestResponse.success(oneProduct);
    }

    @PostMapping("/product")
    public ApiRestResponse postProduct(@Valid ProductReq productReq) {
        Product oneProduct = productService.createOneProduct(productReq);
        return ApiRestResponse.success(oneProduct);
    }

    @RequestMapping(value = "/upload/image", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ApiRestResponse postUploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws UnsupportedEncodingException {
        String uploadFile = productService.uploadFile(file);
        //协议+ip地址+端口号
        String format = MessageFormat.format("{0}://{1}:{2}/static/{3}", request.getScheme(), request.getServerName(), String.valueOf(request.getServerPort()), uploadFile);
        return ApiRestResponse.success(format);
    }

}
