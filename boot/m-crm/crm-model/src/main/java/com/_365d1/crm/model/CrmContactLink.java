package com._365d1.crm.model;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 联系人联系方式表
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-16 12:31
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

@Data
@TableName(value = "crm_contact_link")
public class CrmContactLink implements Serializable {

	public static final String ID = "id";
	public static final String CONTACT_ID = "contact_id";
	public static final String TYPE = "type";
	public static final String DATA_VALUE = "data_value";

	/**
	 * ID
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;

	/**
	 * 联系人ID
	 */
	@TableField(value = CONTACT_ID)
	private String contactId;

	/**
	 * 类型[0 手机 | 1微信 | 2 地址 | 3 电话 | 4 QQ | 5 邮箱]
	 */
	@TableField(value = TYPE)
	private Integer type;

	/**
	 * 数据值
	 */
	@TableField(value = DATA_VALUE)
	private String dataValue;

}