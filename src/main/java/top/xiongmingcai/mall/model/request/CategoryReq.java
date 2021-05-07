package top.xiongmingcai.mall.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 */
public class CategoryReq {

    private Integer id;
    @NotBlank(message = "分类名称不能为空")
    private String name;
    @NotNull(message = "分类编号为必填项")
    private Integer type;
    @NotNull(message = "上一级目录必填项")
    private Integer parentId;
    @NotNull(message = "排序必填项")
    private Integer orderNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "CategoryReq{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", orderNum=" + orderNum +
                '}';
    }
}
