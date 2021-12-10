package com._365d1.crm.model;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 产品表
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-25 16:42
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

@Data
@TableName(value = "crm_product")
public class CrmProduct implements Serializable {

	public static final String ID = "id";
	public static final String CATEGORY_ID = "category_id";
	public static final String SERIAL = "serial";
	public static final String NAME = "name";
	public static final String COST_PRICE = "cost_price";
	public static final String SELLING_PRICE = "selling_price";
	public static final String UNIT = "unit";
	public static final String CREATE_TIME = "create_time";
	public static final String REMARK = "remark";
	public static final String STATUS = "status";

	/**
	 * ID
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;

	/**
	 * 所属分类
	 */
	@TableField(value = CATEGORY_ID)
	private String categoryId;

	/**
	 * 产品编号
	 */
	@TableField(value = SERIAL)
	private String serial;

	/**
	 * 产品名称
	 */
	@TableField(value = NAME)
	private String name;

	/**
	 * 成本价
	 */
	@TableField(value = COST_PRICE)
	private BigDecimal costPrice;

	/**
	 * 销售价
	 */
	@TableField(value = SELLING_PRICE)
	private BigDecimal sellingPrice;

	/**
	 * 单位
	 */
	@TableField(value = UNIT)
	private String unit;

	/**
	 * 创建时间
	 */
	@TableField(value = CREATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp createTime;

	/**
	 * 备注
	 */
	@TableField(value = REMARK)
	private String remark;

	/**
	 * 状态[0 停售 | 1 在售]
	 */
	@TableField(value = STATUS)
	private Integer status;

}