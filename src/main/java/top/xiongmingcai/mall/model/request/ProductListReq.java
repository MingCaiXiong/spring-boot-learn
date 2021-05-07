package top.xiongmingcai.mall.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductListReq {

    @NotBlank(message = "商品名称不能为空")
    private String keyWord;

    @NotNull(message = "商品分类编号为必填项")
    private Integer categoryId;

    private String orderBy;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public String toString() {
        return "ProductListReq{" +
                "keyWord='" + keyWord + '\'' +
                ", categoryId=" + categoryId +
                ", orderBy='" + orderBy + '\'' +
                '}';
    }
}
