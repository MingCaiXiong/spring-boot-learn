package top.xiongmingcai.mall.service;


import top.xiongmingcai.mall.model.request.CreateOrderReq;

/**
 * Service：订单表;
 *
 * @author
 * @date 2021-05-05 17:05:35
 */
public interface OrderService {

    String createOrder(CreateOrderReq createOrderReq, Integer userId);
}