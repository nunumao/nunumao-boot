package com._365d1.crm.model;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 商机签单条件表
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-19 15:21
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
@TableName(value = "crm_chance_condition")
public class CrmChanceCondition implements Serializable {

	public static final String ID = "id";
	public static final String CHANCE_ID = "chance_id";
	public static final String CONTENT = "content";
	public static final String STATUS = "status";

	/**
	 * ID
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;

	/**
	 * 商机ID
	 */
	@TableField(value = CHANCE_ID)
	private String chanceId;

	/**
	 * 条件
	 */
	@TableField(value = CONTENT)
	private String content;

	/**
	 * 状态[0 未达成 | 1 已达成]
	 */
	@TableField(value = STATUS)
	private Integer status;

}