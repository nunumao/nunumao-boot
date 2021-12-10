package com._365d1.crm.model;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 联系人表
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-16 10:36
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
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
@TableName(value = "crm_contact")
public class CrmContact implements Serializable {

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String LEAD_ID = "lead_id";
    public static final String ASSIGN_USER_ID = "assign_user_id";
    public static final String ASSIGN_TIME = "assign_time";
    public static final String CREATOR_USER_ID = "creator_user_id";
    public static final String EDITOR_USER_ID = "editor_user_id";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String SERIAL = "serial";
    public static final String NAME = "name";
    public static final String INITIAL = "initial";
    public static final String AVATAR = "avatar";
    public static final String GENDER = "gender";
    public static final String COMPANY = "company";
    public static final String POSITION = "position";
    public static final String DEPARTMENT = "department";
    public static final String ROLE = "role";
    public static final String RELATION = "relation";
    public static final String BIRTHDAY = "birthday";
    public static final String HOBBY = "hobby";
    public static final String REMARK = "remark";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";
    public static final String LAST_TIME = "last_time";
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
     * 关联客户
     */
    @TableField(value = CUSTOMER_ID)
    private String customerId;

    /**
     * 编号
     */
    @TableField(value = SERIAL)
    private String serial;

    /**
     * 联系人姓名
     */
    @TableField(value = NAME)
    private String name;

    /**
     * 联系人首字母
     */
    @TableField(value = INITIAL)
    private String initial;

    /**
     * 头像
     */
    @TableField(value = AVATAR)
    private String avatar;

    /**
     * 性别[0 未知 | 1 男 | 2 女]
     */
    @TableField(value = GENDER)
    private Integer gender;

    /**
     * 公司名称
     */
    @TableField(value = COMPANY)
    private String company;

    /**
     * 岗位
     */
    @TableField(value = POSITION)
    private String position;

    /**
     * 部门
     */
    @TableField(value = DEPARTMENT)
    private String department;

    /**
     * 角色关系[0 经办人 | 1 决策人 | 2 使用人 | 3 意见影响人 | 4 商务决策]
     */
    @TableField(value = ROLE)
    private Integer role;

    /**
     * 亲密关系[0 初相识 | 1 一般关系 | 2 朋友关系 | 3 好友关系]
     */
    @TableField(value = RELATION)
    private Integer relation;

    /**
     * 生日
     */
    @TableField(value = BIRTHDAY)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    /**
     * 爱好
     */
    @TableField(value = HOBBY)
    private String hobby;

    /**
     * 备注
     */
    @TableField(value = REMARK)
    private String remark;

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
     * 最后接触时间
     */
    @TableField(value = LAST_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastTime;

    /**
     * 状态
     */
    @TableField(value = STATUS)
    private Integer status;

}