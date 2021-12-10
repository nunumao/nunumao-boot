package com._365d1.crm.model.vo;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 西途比科技代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-25 01:01
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CrmProductVo implements Serializable {

	private String id;
	private String categoryId;
	private String serial;
	private String name;
	private BigDecimal costPrice;
	private BigDecimal sellingPrice;
	private String unit;
	private Integer status;
	private String remark;
	private String category;

}
