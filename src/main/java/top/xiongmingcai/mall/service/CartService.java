package top.xiongmingcai.mall.service;

import top.xiongmingcai.mall.model.vo.CartVo;

import java.util.List;

public interface CartService {
    List<CartVo> list(Integer userId);

    List<CartVo> addCart(Integer userId, Integer productId, Integer count);

    List<CartVo> deleteCart(Integer userId, Integer productId);

    List<CartVo> sekectOrNot(Integer userId, Integer productId, Integer sekected);

    List<CartVo> sekectOrNot(Integer userId, Integer sekected);

}
