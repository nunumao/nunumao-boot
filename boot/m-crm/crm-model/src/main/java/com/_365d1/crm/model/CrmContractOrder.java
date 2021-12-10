package com._365d1.crm.model;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 合同项目表
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-27 18:08
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

@Data
@TableName(value = "crm_contract_order")
public class CrmContractOrder implements Serializable {

	public static final String ID = "id";
	public static final String CONTRACT_ID = "contract_id";
	public static final String NAME = "name";
	public static final String ORIGIN_PRICE = "origin_price";
	public static final String PRICE = "price";
	public static final String QUANTITY = "quantity";
	public static final String UNIT = "unit";
	public static final String TOTAL_PRICE = "total_price";

	/**
	 * ID
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;

	/**
	 * 合同ID
	 */
	@TableField(value = CONTRACT_ID)
	private String contractId;

	/**
	 * 项目名称
	 */
	@TableField(value = NAME)
	private String name;

	/**
	 * 原价
	 */
	@TableField(value = ORIGIN_PRICE)
	private BigDecimal originPrice;

	/**
	 * 成交价
	 */
	@TableField(value = PRICE)
	private BigDecimal price;

	/**
	 * 数量
	 */
	@TableField(value = QUANTITY)
	private Integer quantity;

	/**
	 * 单位
	 */
	@TableField(value = UNIT)
	private String unit;

	/**
	 * 总价
	 */
	@TableField(value = TOTAL_PRICE)
	private BigDecimal totalPrice;

}