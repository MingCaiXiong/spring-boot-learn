package top.xiongmingcai.mall.service.impl;


import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import top.xiongmingcai.mall.exception.BussinessException;
import top.xiongmingcai.mall.exception.ExceptionEnum;
import top.xiongmingcai.mall.model.dao.CartMapper;
import top.xiongmingcai.mall.model.dao.ProductMapper;
import top.xiongmingcai.mall.model.pojo.Order;
import top.xiongmingcai.mall.model.pojo.OrderItem;
import top.xiongmingcai.mall.model.pojo.Product;
import top.xiongmingcai.mall.model.request.CreateOrderReq;
import top.xiongmingcai.mall.model.vo.CartVo;
import top.xiongmingcai.mall.service.CartService;
import top.xiongmingcai.mall.service.OrderService;
import top.xiongmingcai.mall.util.OrderIdUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static top.xiongmingcai.mall.common.Constant.selected.CHECKED;

/**
 * Service：订单表;
 *
 * @author
 * @date 2021-05-05 17:10:39
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private CartService cartService;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private CartMapper cartMapper;

    @Override
    public void createOrder(CreateOrderReq createOrderReq, Integer userId) {
        // 获得购物车
        List<CartVo> cartVoList = cartService.list(userId);
        // 过滤出选中的购物车商品
        List<CartVo> selectedCartVos = cartVoList.stream()
                .filter(item -> item.getSelected().equals(CHECKED))
                .collect(Collectors.toList());
        //没有选中的购物车商品
        if (CollectionUtils.isEmpty(cartVoList)) {
            throw new BussinessException(ExceptionEnum.CART_EMPTY);
        }

        //验证销售状态和库存
        selectedCartVos.forEach(item -> {
            cartService.validProduct(item.getProductId(), item.getQuantity());
        });
        //将购物转换成订单Item
        List<OrderItem> orderItemList = new ArrayList<>();
        selectedCartVos.forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setProductName(cartItem.getProductName());
            orderItem.setProductImg(cartItem.getProductImage());
            orderItem.setUnitPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItemList.add(orderItem);
        });

        //扣库存
        orderItemList.forEach(orderItem -> {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            int stock = product.getStock() - orderItem.getQuantity();
            if (stock < 0) {
                throw new BussinessException(ExceptionEnum.Not_ENOUGH);
            }

            product.setStock(stock);
            productMapper.updateByPrimaryKeySelective(product);
        });
        //删除已选
        selectedCartVos.forEach(item -> {
            cartMapper.deleteByPrimaryKey(item.getId());
        });
        //生成订单
        String orderNum = OrderIdUtils.getorder(Long.valueOf(userId));
        Order order = new Order();
        order.setOrderNo(orderNum);
    }

}