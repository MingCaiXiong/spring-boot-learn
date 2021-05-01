package top.xiongmingcai.mall.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 */
public class CategoryReq {
    @NotBlank(message = "分类名称不能为空")
    private String name;
    @NotNull(message = "分类编号为必填项")
    private Integer type;
    @NotNull(message = "上一级目录必填项")
    private Integer parentId;
    @NotNull(message = "排序必填项")
    private Integer orderNum;

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
}
