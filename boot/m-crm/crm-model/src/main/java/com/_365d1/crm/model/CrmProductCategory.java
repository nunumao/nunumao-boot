package com._365d1.crm.model;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 产品分类表
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-23 23:16
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.nunumao.common.utils.tree.Tree;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "crm_product_category")
public class CrmProductCategory implements Tree<CrmProductCategory> {

    public static final String ID = "id";
    public static final String PARENT_ID = "parent_id";
    public static final String NAME = "name";
    public static final String SORT = "sort";

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 上级ID
     */
    @TableField(value = PARENT_ID)
    private String parentId;

    /**
     * 名称
     */
    @TableField(value = NAME)
    private String name;

    /**
     * 排序
     */
    @TableField(value = SORT)
    private Integer sort;

    @TableField(exist = false)
    private List<CrmProductCategory> child;

    @Override
    public void setChild(List<CrmProductCategory> child) {
        this.child = child;
    }

}