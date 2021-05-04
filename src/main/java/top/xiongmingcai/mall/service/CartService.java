package top.xiongmingcai.mall.service;

import top.xiongmingcai.mall.model.vo.CartVo;

import java.util.List;

public interface CartService {
    List<CartVo> addCart(Integer userId, Integer productId, Integer count);
}
