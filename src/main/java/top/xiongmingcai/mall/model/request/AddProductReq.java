package top.xiongmingcai.mall.model.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddProductReq {

    @NotBlank(message = "商品名称不能为空")
    private String name;

    @NotBlank(message = "商品图片不能为空")
    @Length(min = 6, message = "商品图片长度不能少于6位")
    private String image;

    @NotBlank(message = "商品详情不能为空")
    private String detail;

    @NotNull(message = "商品分类编号为必填项")
    private Integer categoryId;

    @NotNull(message = "商品价格为必填项")
    @Min(value = 1, message = "价格不能小于一分")
    private Integer price;

    @NotNull(message = "商品库存为必填项")
    @Min(value = 1, message = "商品库存不能小于1")
    @Max(value = 10000, message = "商品库存不能大于10000")
    private Integer stock;

    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AddProductReq{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", detail='" + detail + '\'' +
                ", categoryId=" + categoryId +
                ", price=" + price +
                ", stock=" + stock +
                ", status=" + status +
                '}';
    }
}
