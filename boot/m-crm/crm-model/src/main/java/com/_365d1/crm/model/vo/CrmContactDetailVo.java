package com._365d1.crm.model.vo;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 西途比科技代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-16 17:11
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.crm.model.CrmContactLink;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
public class CrmContactDetailVo implements Serializable {

    private String id;
    private String userId;
    private String customerId;
    private String serial;
    private String name;
    private String initial;
    private String avatar;
    private Integer gender;
    private String company;
    private String position;
    private String department;
    private Integer role;
    private Integer relation;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
    private String hobby;
    private String remark;
    private Integer status;
    private String user;
    private String customer;
    private List<CrmContactLink> linkList;

}
