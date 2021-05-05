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
    public List<CartVo> list(Integer userId) {
        List<CartVo> cartVoList = cartMapper.list(userId);
        for (CartVo item : cartVoList) {
            item.setTotalPrice(item.getPrice() * item.getQuantity());
        }
        return cartVoList;
    }

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
            Cart update = cartMapper.selectByPrimaryKey(cart.getId());
            update.setQuantity(update.getQuantity() + count);
            //   '是否已勾选：0代表未勾选，1代表已勾选'
            update.setSelected(Constant.selected.CHECKED);
            cartMapper.updateByPrimaryKeySelective(update);
        }
        return list(userId);
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

    @Override
    public List<CartVo> deleteCart(Integer userId, Integer productId) {
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);

        if (cart == null) {
            //不在购物车
            throw new BussinessException(ExceptionEnum.ORDER_DOES_NOT_EXIST);
        } else {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return list(userId);
    }

    /**
     * 选择是否
     *
     * @param userId
     * @param productId
     * @param sekected  是否已勾选：0代表未勾选，1代表已勾选
     * @return
     */
    @Override
    public List<CartVo> sekectOrNot(Integer userId, Integer productId, Integer sekected) {
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);

        if (cart == null) {
            //不在购物车
            throw new BussinessException(ExceptionEnum.ORDER_DOES_NOT_EXIST);
        } else {
            cartMapper.updateByProductIdsekectOrNot(userId, productId, sekected);
        }
        return list(userId);
    }


    /**
     * 购物车全部选择是否
     *
     * @param userId
     * @param sekected
     * @return
     */
    @Override
    public List<CartVo> sekectOrNot(Integer userId, Integer sekected) {
        cartMapper.updateByProductIdsekectOrNot(userId, null, sekected);
        return list(userId);
    }

}
