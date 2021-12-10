package com._365d1.crm.model;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 跟进记录表
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-20 12:29
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
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

@Data
@TableName(value = "crm_event")
public class CrmEvent implements Serializable {

	public static final String ID = "id";
	public static final String USER_ID = "user_id";
	public static final String EDITOR_USER_ID = "editor_user_id";
	public static final String CONTENT = "content";
	public static final String CUSTOMER_ID = "customer_id";
	public static final String CONTACT_ID = "contact_id";
	public static final String CHANCE_ID = "chance_id";
	public static final String LOCATION = "location";
	public static final String RESULT = "result";
	public static final String EVENT_DATE = "event_date";
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
	 * 编辑用户
	 */
	@TableField(value = EDITOR_USER_ID)
	private String editorUserId;

	/**
	 * 事件内容
	 */
	@TableField(value = CONTENT)
	private String content;

	/**
	 * 所属客户ID
	 */
	@TableField(value = CUSTOMER_ID)
	private String customerId;

	/**
	 * 所属联系人ID
	 */
	@TableField(value = CONTACT_ID)
	private String contactId;

	/**
	 * 所属商机ID
	 */
	@TableField(value = CHANCE_ID)
	private String chanceId;

	/**
	 * 事件地点[0 电话联系 | 1 线上沟通 | 2 邮件沟通 | 3 上门洽谈 | 4 公司会谈]
	 */
	@TableField(value = LOCATION)
	private Integer location;

	/**
	 * 事件结果[0 客户不满意 | 1 客户满意 | 2 客户无表示 | 3 未联系上客户]
	 */
	@TableField(value = RESULT)
	private Integer result;

	/**
	 * 事件日期
	 */
	@TableField(value = EVENT_DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date eventDate;

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