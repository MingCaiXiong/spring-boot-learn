package top.xiongmingcai.mall.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.xiongmingcai.mall.common.Constant;
import top.xiongmingcai.mall.exception.BussinessException;
import top.xiongmingcai.mall.exception.ExceptionEnum;
import top.xiongmingcai.mall.model.dao.CartMapper;
import top.xiongmingcai.mall.model.dao.OrderItemMapper;
import top.xiongmingcai.mall.model.dao.OrderMapper;
import top.xiongmingcai.mall.model.dao.ProductMapper;
import top.xiongmingcai.mall.model.pojo.Order;
import top.xiongmingcai.mall.model.pojo.OrderItem;
import top.xiongmingcai.mall.model.pojo.Product;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.model.request.CreateOrderReq;
import top.xiongmingcai.mall.model.vo.CartVo;
import top.xiongmingcai.mall.model.vo.OrderItemVo;
import top.xiongmingcai.mall.model.vo.OrderVo;
import top.xiongmingcai.mall.service.CartService;
import top.xiongmingcai.mall.service.OrderService;
import top.xiongmingcai.mall.service.UserService;
import top.xiongmingcai.mall.util.OrderIdUtils;
import top.xiongmingcai.mall.util.QRCodeUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserService userService;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Value("${file.upload.ip}")
    private String SERVER_NAME;

    @Value("${file.upload.dir}")
    private String FILE_UPLOAD_DIR;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String createOrder(CreateOrderReq body, Integer userId) {
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
        order.setUserId(userId);
        order.setTotalPrice(totalPrice(orderItemList));
        order.setReceiverName(body.getReceiverName());
        order.setReceiverMobile(body.getReceiverMobile());
        order.setReceiverAddress(body.getReceiverAddress());
        order.setOrderStatus(Constant.OrderStatusEnum.UNPAID.getCode());
        order.setPostage(0);
        order.setPaymentType(1);
        orderMapper.insertSelective(order);

        orderItemList.forEach(orderItem -> {
            orderItem.setOrderNo(order.getOrderNo());
            orderItemMapper.insertSelective(orderItem);
        });
        return orderNum;
    }

    @Override
    public OrderVo orderInfo(String orderNo, Integer userId) {
        Order order = verifyOrder(orderNo, userId);
        return getOrderVo(order);
    }

    /**
     * 验证订单有效性
     *
     * @param orderNo
     * @param userId  当前登录的用户
     * @return 当前登录的用户下的一条有效订单
     */
    private Order verifyOrder(String orderNo, Integer userId) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new BussinessException(ExceptionEnum.ORDER_DOES_NOT_EXIST);
        }
        if (!order.getUserId().equals(userId)) {
            throw new BussinessException(ExceptionEnum.ORDER__DOES_NOT_BELONG_TO_YOU);
        }
        return order;
    }

    /**
     * 订单视图对象拼装
     *
     * @param order
     * @return
     */
    private OrderVo getOrderVo(Order order) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        String statusMessage = Constant.OrderStatusEnum.codeof(orderVo.getOrderStatus()).getValue();
        orderVo.setOrderStatusName(statusMessage);

        //查询订单中的所有商品
        List<OrderItem> orderItem = orderItemMapper.selectByOrderNo(order.getOrderNo());
        // List<OrderItem>  转换为 List<OrderItemVo>
        List<OrderItemVo> orderItemVos = orderItem.stream().map(orderItem1 -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(orderItem1, orderItemVo);
            return orderItemVo;

        }).collect(Collectors.toList());

        orderVo.setOrderItemVoList(orderItemVos);
        return orderVo;
    }

    @Override
    public PageInfo findOneOrders(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orders = orderMapper.selectByUserIdOrderByCreateTimeDesc(userId);
        List<OrderVo> orderVoList = orders.stream()
                .filter(Objects::nonNull)
                .map(this::getOrderVo)
                .collect(Collectors.toList());
        PageInfo<OrderVo> pageInfo = new PageInfo<>(orderVoList);
        pageInfo.setList(orderVoList);
        return pageInfo;
    }


    @Override
    public PageInfo findAllOrdersForAdmin(Integer id, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orders = orderMapper.selectAllOrderByCreateTimeDesc();
        List<OrderVo> orderVoList = orders.stream()
                .filter(order -> order.getTotalPrice() != null)
                .map(this::getOrderVo)
                .collect(Collectors.toList());
        PageInfo<OrderVo> pageInfo = new PageInfo<>(orderVoList);
        pageInfo.setList(orderVoList);
        return pageInfo;
    }

    @Override
    public void cancelOrders(String orderNo, Integer userId) {
        Order verifyOrder = verifyOrder(orderNo, userId);
        if (verifyOrder.getOrderStatus().equals(Constant.OrderStatusEnum.UNPAID.getCode())) {
            verifyOrder.setOrderStatus(Constant.OrderStatusEnum.CANCEL.getCode());
            verifyOrder.setEndTime(new Date());
            orderMapper.updateByPrimaryKeySelective(verifyOrder);
        } else {
            throw new BussinessException(ExceptionEnum.ORDER_STATUS_DOES_NOT_MATCH);
        }
    }

    /**
     * 订单金额累加
     *
     * @param orderItemList
     * @return
     */
    private Integer totalPrice(List<OrderItem> orderItemList) {
        return orderItemList.stream()
                .filter(Objects::nonNull)
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

    /**
     * 生成扫描支付图片
     *
     * @param orderNo
     * @return 二维码图片可访问地址
     * @throws IOException
     * @throws WriterException
     */
    @Override
    public String qrcode(String orderNo, Integer userId) throws IOException, WriterException {
        verifyOrder(orderNo, userId);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String payurl = MessageFormat.format("{0}://{1}:{2}/pay/{3}", request.getScheme(), SERVER_NAME, String.valueOf(request.getServerPort()), orderNo);
        QRCodeUtils.generateQRCodeImage(payurl, 350, 350, FILE_UPLOAD_DIR + "/images/" + orderNo + ".png");
        //图片可访问地址
        return MessageFormat.format("{0}://{1}:{2}/static/images/{3}.png", request.getScheme(), SERVER_NAME, String.valueOf(request.getServerPort()), orderNo);
    }

    @Override
    public void pay(String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new BussinessException(ExceptionEnum.ORDER_DOES_NOT_EXIST);
        }
        if (order.getOrderStatus() != Constant.OrderStatusEnum.UNPAID.getCode()) {
            throw new BussinessException(ExceptionEnum.ORDER_STATUS_DOES_NOT_MATCH);
        } else {
            order.setOrderStatus(Constant.OrderStatusEnum.PAID.getCode());
            order.setPayTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        }
    }

    /**
     * 管理员发货
     *
     * @param userId
     * @param orderNo
     */
    @Override
    public void delivered(Integer userId, String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new BussinessException(ExceptionEnum.ORDER_DOES_NOT_EXIST);
        }
        //0用户已取消，10未付款（初始状态），20已付款，30已发货，40交易完成
        int cancel = Constant.OrderStatusEnum.CANCEL.getCode();//0用户已取消
        int UNPAID = Constant.OrderStatusEnum.UNPAID.getCode();//10未付款
        int paid = Constant.OrderStatusEnum.PAID.getCode();//20已付款
        int delvered = Constant.OrderStatusEnum.DELIVERED.getCode();//30已发货

        if (order.getOrderStatus() == cancel) {
            throw new BussinessException(ExceptionEnum.ORDER_CANCELLED);
        } else if (order.getOrderStatus() == delvered) {
            throw new BussinessException(ExceptionEnum.ORDER_HAS_BEEN_SHIPPED);
        }
        if (order.getOrderStatus() == UNPAID) {
            throw new BussinessException(ExceptionEnum.ORDER_UNPAID);
        } else if (order.getOrderStatus() == paid) {
            order.setOrderStatus(delvered);
            order.setDeliveryTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new BussinessException(ExceptionEnum.ORDER_STATUS_DOES_NOT_MATCH);
        }
    }

    @Override
    public void finish(User user, String orderNo) {
        Order order = verifyOrder(orderNo, user.getId());

        if (!userService.checkPermissions(user) && !order.getUserId().equals(user.getId())) {
            throw new BussinessException(ExceptionEnum.INSUFFICIENT_PERMISSIONS);
        }
        if (order.getOrderStatus() == Constant.OrderStatusEnum.FINISH.getCode()) {
            throw new BussinessException(ExceptionEnum.ORDER_STATUS_DOES_NOT_MATCH.getCode(), Constant.OrderStatusEnum.FINISH.getValue());
        } else if (order.getOrderStatus() == Constant.OrderStatusEnum.DELIVERED.getCode()) {
            order.setOrderStatus(Constant.OrderStatusEnum.FINISH.getCode());
            order.setEndTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new BussinessException(ExceptionEnum.ORDER_STATUS_DOES_NOT_MATCH);
        }
    }
}