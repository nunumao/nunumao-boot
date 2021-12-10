package com._365d1.crm.model.vo;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 西途比科技代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-17 06:17
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class CrmChanceDetailVo implements Serializable {

	private String id;
	private String userId;
	private String serial;
	private String name;
	private String customerId;
	private String contactId;
	private Double expectIncome;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date expectDate;
	private Integer probability;
	private Integer progress;
	private String remark;
	private Integer status;
	private String user;
	private String customer;
	private String contact;

}
