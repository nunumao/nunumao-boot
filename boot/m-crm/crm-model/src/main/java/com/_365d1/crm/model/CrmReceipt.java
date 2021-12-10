package com._365d1.crm.model;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 收款表
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-29 03:46
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
import java.io.Serializable;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

@Data
@TableName(value = "crm_receipt")
public class CrmReceipt implements Serializable {

	public static final String ID = "id";
	public static final String USER_ID = "user_id";
	public static final String CREATOR_USER_ID = "creator_user_id";
	public static final String EDITOR_USER_ID = "editor_user_id";
	public static final String CONTRACT_ID = "contract_id";
	public static final String PAY_TYPE = "pay_type";
	public static final String PRICE = "price";
	public static final String PRICE_DATE = "price_date";
	public static final String PLAN_PRICE = "plan_price";
	public static final String PLAN_DATE = "plan_date";
	public static final String DIFF = "diff";
	public static final String BILL_TYPE = "bill_type";
	public static final String BILL_SERIAL = "bill_serial";
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
	 * 用户ID
	 */
	@TableField(value = USER_ID)
	private String userId;

	/**
	 * 创建者ID
	 */
	@TableField(value = CREATOR_USER_ID)
	private String creatorUserId;

	/**
	 * 编辑者ID
	 */
	@TableField(value = EDITOR_USER_ID)
	private String editorUserId;

	/**
	 * 合同ID
	 */
	@TableField(value = CONTRACT_ID)
	private String contractId;

	/**
	 * 收款方式[0 银行转账 | 1 微信支付 | 2 支付宝 | 3 网银转账 | 4 现金 | 5 其他]
	 */
	@TableField(value = PAY_TYPE)
	private Integer payType;

	/**
	 * 本次到账
	 */
	@TableField(value = PRICE)
	private BigDecimal price;

	/**
	 * 收款时间
	 */
	@TableField(value = PRICE_DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date priceDate;

	/**
	 * 计划到账
	 */
	@TableField(value = PLAN_PRICE)
	private BigDecimal planPrice;

	/**
	 * 计划时间
	 */
	@TableField(value = PLAN_DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date planDate;

	/**
	 * 实收与计划差距
	 */
	@TableField(value = DIFF)
	private BigDecimal diff;

	/**
	 * 票据类型[0 发票 | 1 收据 | 2 其他]
	 */
	@TableField(value = BILL_TYPE)
	private Integer billType;

	/**
	 * 票据编号
	 */
	@TableField(value = BILL_SERIAL)
	private String billSerial;

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
	 * 状态
	 */
	@TableField(value = STATUS)
	private Integer status;

}