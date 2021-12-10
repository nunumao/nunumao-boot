package com._365d1.crm.model;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 合同表
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-27 17:56
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
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
@TableName(value = "crm_contract")
public class CrmContract implements Serializable {

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String CREATOR_USER_ID = "creator_user_id";
    public static final String EDITOR_USER_ID = "editor_user_id";
    public static final String SERIAL = "serial";
    public static final String NAME = "name";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CHANCE_ID = "chance_id";
    public static final String TYPE = "type";
    public static final String TOTAL_PRICE = "total_price";
    public static final String COLLECT_PRICE = "collect_price";
    public static final String SURPLUS_PRICE = "surplus_price";
    public static final String REBATE_PRICE = "rebate_price";
    public static final String SIGN_DATE = "sign_date";
    public static final String EXPIRE_DATE = "expire_date";
    public static final String REMARK = "remark";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";
    public static final String STATUS = "status";

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 所属用户
     */
    @TableField(value = USER_ID)
    private String userId;

    /**
     * 创建人
     */
    @TableField(value = CREATOR_USER_ID)
    private String creatorUserId;

    /**
     * 编辑人
     */
    @TableField(value = EDITOR_USER_ID)
    private String editorUserId;

    /**
     * 合同编号
     */
    @TableField(value = SERIAL)
    private String serial;

    /**
     * 合同名称
     */
    @TableField(value = NAME)
    private String name;

    /**
     * 客户ID
     */
    @TableField(value = CUSTOMER_ID)
    private String customerId;

    /**
     * 商机ID
     */
    @TableField(value = CHANCE_ID)
    private String chanceId;

    /**
     * 合同类型[0 产品合同 | 1 服务合同 | 2 实施合同 | 3 续费合同 | 4 代理合同]
     */
    @TableField(value = TYPE)
    private Integer type;

    /**
     * 总金额
     */
    @TableField(value = TOTAL_PRICE)
    private BigDecimal totalPrice;

    /**
     * 已收款
     */
    @TableField(value = COLLECT_PRICE)
    private BigDecimal collectPrice;

    /**
     * 未收款
     */
    @TableField(value = SURPLUS_PRICE)
    private BigDecimal surplusPrice;

    /**
     * 商务费用
     */
    @TableField(value = REBATE_PRICE)
    private BigDecimal rebatePrice;

    /**
     * 签订日期
     */
    @TableField(value = SIGN_DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date signDate;

    /**
     * 合同期限[留空为永久]
     */
    @TableField(value = EXPIRE_DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date expireDate;

    /**
     * 备注
     */
    @TableField(value = REMARK)
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = CREATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @TableField(value = UPDATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateTime;

    /**
     * 状态[0 未执行 | 1 执行中 | 2 结束 | 3 终止]
     */
    @TableField(value = STATUS)
    private Integer status;

    @TableField(exist = false)
    private List<CrmContractOrder> orderList;

}