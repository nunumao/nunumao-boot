package com._365d1.crm.model;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 商机表
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-15 15:36
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
@TableName(value = "crm_chance")
public class CrmChance implements Serializable {

	public static final String ID = "id";
	public static final String USER_ID = "user_id";
	public static final String ASSIGN_USER_ID = "assign_user_id";
	public static final String ASSIGN_TIME = "assign_time";
	public static final String CREATOR_USER_ID = "creator_user_id";
	public static final String EDITOR_USER_ID = "editor_user_id";
	public static final String SERIAL = "serial";
	public static final String NAME = "name";
	public static final String CUSTOMER_ID = "customer_id";
	public static final String CONTACT_ID = "contact_id";
	public static final String EXPECT_INCOME = "expect_income";
	public static final String EXPECT_DATE = "expect_date";
	public static final String PROBABILITY = "probability";
	public static final String ACTUAL_INCOME = "actual_income";
	public static final String ACTUAL_TIME = "actual_time";
	public static final String PROGRESS = "progress";
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
	 * 所属用户ID
	 */
	@TableField(value = USER_ID)
	private String userId;

	/**
	 * 分配者ID
	 */
	@TableField(value = ASSIGN_USER_ID)
	private String assignUserId;

	/**
	 * 分配时间
	 */
	@TableField(value = ASSIGN_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp assignTime;

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
	 * 商机编号
	 */
	@TableField(value = SERIAL)
	private String serial;

	/**
	 * 商机名称
	 */
	@TableField(value = NAME)
	private String name;

	/**
	 * 关联客户
	 */
	@TableField(value = CUSTOMER_ID)
	private String customerId;

	/**
	 * 关联联系人
	 */
	@TableField(value = CONTACT_ID)
	private String contactId;

	/**
	 * 预期收入
	 */
	@TableField(value = EXPECT_INCOME)
	private BigDecimal expectIncome;

	/**
	 * 预期时间
	 */
	@TableField(value = EXPECT_DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date expectDate;

	/**
	 * 成交机率
	 */
	@TableField(value = PROBABILITY)
	private Integer probability;

	/**
	 * 实际收入
	 */
	@TableField(value = ACTUAL_INCOME)
	private BigDecimal actualIncome;

	/**
	 * 实际收入时间
	 */
	@TableField(value = ACTUAL_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp actualTime;

	/**
	 * 商机进度[]
	 */
	@TableField(value = PROGRESS)
	private Integer progress;

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
	 * 状态[0 执行中 | 1 成交 | 2 失败]
	 */
	@TableField(value = STATUS)
	private Integer status;

}