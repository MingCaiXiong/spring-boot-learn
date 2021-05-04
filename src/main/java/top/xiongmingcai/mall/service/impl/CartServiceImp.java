package top.xiongmingcai.mall.service.impl;

import org.springframework.stereotype.Service;
import top.xiongmingcai.mall.common.Constant;
import top.xiongmingcai.mall.exception.BussinessException;
import top.xiongmingcai.mall.exception.ExceptionEnum;
import top.xiongmingcai.mall.model.dao.CartMapper;
import top.xiongmingcai.mall.model.dao.ProductMapper;
import top.xiongmingcai.mall.model.pojo.Cart;
import top.xiongmingcai.mall.model.pojo.Product;
import top.xiongmingcai.mall.model.vo.CartVo;
import top.xiongmingcai.mall.service.CartService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 购物车服务实现类
 */
@Service
public class CartServiceImp implements CartService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private CartMapper cartMapper;

    @Override
    public List<CartVo> addCart(Integer userId, Integer productId, Integer count) {
        validProduct(productId, count);
        // ③ 查询是否存在购物车记录
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        if (cart == null) {
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            //   '是否已勾选：0代表未勾选，1代表已勾选'
            cart.setSelected(Constant.selected.CHECKED);
            cartMapper.insertSelective(cart);
        } else {
            // ④ 商品存在于购物车记录则 数量修改
            count = cart.getQuantity() + count;
            Cart update = new Cart();
            update.setProductId(cart.getProductId());
            update.setUserId(cart.getUserId());
            update.setQuantity(count);
            //   '是否已勾选：0代表未勾选，1代表已勾选'
            update.setSelected(Constant.selected.CHECKED);
            cartMapper.updateByPrimaryKeySelective(update);
        }
        return null;
    }

    private void validProduct(Integer productId, Integer count) {
        Product product = productMapper.selectByPrimaryKey(productId);
        // ① 合法验证
        if (product == null || product.getStatus().equals(Constant.ProductStatusEnum.ON_SALE.getCode())) {
            throw new BussinessException(ExceptionEnum.NOT_SALE);
        }
        // ② 验证库存
        if (count > product.getStock()) {
            throw new BussinessException(ExceptionEnum.INVENTORY_SHORTAGE);
        }
    }
}
