package top.xiongmingcai.mall.model.request;


import javax.validation.constraints.NotBlank;

/**
 * 订单请求类
 */
public class CreateOrderReq {
    /**
     * 收货人姓名快照
     */
    @NotBlank(message = "收货人不能为空")
    private String receiverName;

    /**
     * 收货人手机号快照
     */
    @NotBlank(message = "收货人手机号不能为空")
    private String receiverMobile;

    /**
     * 收货地址快照
     */
    @NotBlank(message = "收货地址不能为空")
    private String receiverAddress;


    /**
     * 运费，默认为0
     */
    private Integer postage = 0;

    /**
     * 支付类型,1-在线支付
     */
    private Integer paymentType = 1;

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }


    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return "CreateOrderReq{" +
                "receiverName='" + receiverName + '\'' +
                ", receiverMobile='" + receiverMobile + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", postage=" + postage +
                ", paymentType=" + paymentType +
                '}';
    }
}
