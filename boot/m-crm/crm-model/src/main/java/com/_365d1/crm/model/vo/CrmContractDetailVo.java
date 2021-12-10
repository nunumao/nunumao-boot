package com._365d1.crm.model.vo;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 西途比科技代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-28 20:19
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.crm.model.CrmReceipt;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
public class CrmContractDetailVo implements Serializable {

	private String id;
	private String user;
	private String serial;
	private String name;
	private String customer;
	private String chance;
	private Integer type;
	private BigDecimal totalPrice;
	private BigDecimal collectPrice;
	private BigDecimal surplusPrice;
	private BigDecimal rebatePrice;
	private Date signDate;
	private Date expireDate;
	private Integer status;
	private List<CrmReceipt> receiptList;

}
