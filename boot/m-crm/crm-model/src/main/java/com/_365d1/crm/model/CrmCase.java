package com._365d1.crm.model;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 方案表
// +----------------------------------------------------------------------
// | 创建时间: 2021-12-09 23:22
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
@TableName(value = "crm_case")
public class CrmCase implements Serializable {

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String CONTENT = "content";
	public static final String STATUS = "status";

	/**
	 * ID
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;

	/**
	 * 方案名称
	 */
	@TableField(value = NAME)
	private String name;

	/**
	 * 方案内容
	 */
	@TableField(value = CONTENT)
	private String content;

	/**
	 * 状态
	 */
	@TableField(value = STATUS)
	private Integer status;

}