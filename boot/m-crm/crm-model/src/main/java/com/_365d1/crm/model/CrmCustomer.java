package com._365d1.crm.model;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 客户表
// +----------------------------------------------------------------------
// | 创建时间: 2021-10-16 05:45
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

@Data
@TableName(value = "crm_customer")
public class CrmCustomer implements Serializable {

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String LEAD_ID = "lead_id";
    public static final String ASSIGN_USER_ID = "assign_user_id";
    public static final String ASSIGN_TIME = "assign_time";
    public static final String CREATOR_USER_ID = "creator_user_id";
    public static final String EDITOR_USER_ID = "editor_user_id";
    public static final String LAST_USER_ID = "last_user_id";
    public static final String SEA_ID = "sea_id";
    public static final String SERIAL = "serial";
    public static final String LEVEL = "level";
    public static final String FULL_NAME = "full_name";
    public static final String SHORT_NAME = "short_name";
    public static final String INTRO = "intro";
    public static final String WEBSITE = "website";
    public static final String PHONE = "phone";
    public static final String ZIPCODE = "zipcode";
    public static final String ADDRESS = "address";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";
    public static final String MOVE_TIME = "move_time";
    public static final String STATUS = "status";

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 归属用户ID
     */
    @TableField(value = USER_ID)
    private String userId;

    /**
     * 关联线索
     */
    @TableField(value = LEAD_ID)
    private String leadId;

    /**
     * 分配用户ID
     */
    @TableField(value = ASSIGN_USER_ID)
    private String assignUserId;

    /**
     * 分配时间
     */
    @TableField(value = ASSIGN_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp assignTime;

    /**
     * 创建用户ID
     */
    @TableField(value = CREATOR_USER_ID)
    private String creatorUserId;

    /**
     * 编辑用户ID
     */
    @TableField(value = EDITOR_USER_ID)
    private String editorUserId;

    /**
     * 最终归属用户ID
     */
    @TableField(value = LAST_USER_ID)
    private String lastUserId;

    /**
     * 公海ID
     */
    @TableField(value = SEA_ID)
    private String seaId;

    /**
     * 编号
     */
    @TableField(value = SERIAL)
    private String serial;

    /**
     * 客户分级
     */
    @TableField(value = LEVEL)
    private Integer level;

    /**
     * 客户公司全称
     */
    @TableField(value = FULL_NAME)
    private String fullName;

    /**
     * 客户公司简称
     */
    @TableField(value = SHORT_NAME)
    private String shortName;

    /**
     * 公司简介
     */
    @TableField(value = INTRO)
    private String intro;

    /**
     * 网站
     */
    @TableField(value = WEBSITE)
    private String website;

    /**
     * 手机号码
     */
    @TableField(value = PHONE)
    private String phone;

    /**
     * 邮编
     */
    @TableField(value = ZIPCODE)
    private String zipcode;

    /**
     * 客户地址
     */
    @TableField(value = ADDRESS)
    private String address;

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
     * 移入公海时间
     */
    @TableField(value = MOVE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp moveTime;

    /**
     * 状态
     */
    @TableField(value = STATUS)
    private Integer status;

}