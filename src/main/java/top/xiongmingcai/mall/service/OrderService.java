package top.xiongmingcai.mall.service;


import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.model.request.CreateOrderReq;
import top.xiongmingcai.mall.model.vo.OrderVo;

import java.io.IOException;

/**
 * Service：订单表;
 *
 * @author
 * @date 2021-05-05 17:05:35
 */
public interface OrderService {

    String createOrder(CreateOrderReq createOrderReq, Integer userId);

    OrderVo orderInfo(String orderNo, Integer userId);

    PageInfo findOneOrders(Integer userId, Integer pageNum, Integer pageSize);

    PageInfo findAllOrdersForAdmin(Integer id, Integer pageNum, Integer pageSize);

    void cancelOrders(String orderNo, Integer id);


    String qrcode(String orderNo, Integer userId) throws IOException, WriterException;

    void pay(String orderNo);

    void delivered(Integer id, String orderNo);

    void finish(User user, String orderNo);
}