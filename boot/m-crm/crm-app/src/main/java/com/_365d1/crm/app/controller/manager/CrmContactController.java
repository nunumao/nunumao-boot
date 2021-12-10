package com._365d1.crm.app.controller.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: Nunumao 代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-16 02:44
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com._365d1.crm.app.controller.Manager;
import com._365d1.crm.model.CrmContact;
import com._365d1.crm.model.CrmContactLink;
import com._365d1.crm.model.CrmCustomer;
import com._365d1.crm.model.vo.CrmContactDetailVo;
import com._365d1.crm.model.vo.CrmContactListVo;
import com._365d1.crm.service.CrmContactLinkService;
import com._365d1.crm.service.CrmContactService;
import com._365d1.crm.service.CrmCustomerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.nunumao.common.utils.GsonUtils;
import io.github.nunumao.common.utils.ResultFormat;
import io.github.nunumao.common.utils.SQLDatetime;
import io.github.nunumao.common.utils.login.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@Api(tags = "联系人管理")
@RestController(value = Manager.CRTL_PREFIX + "crmContactController")
@RequestMapping(value = "contact")
public class CrmContactController extends Manager<CrmContactService, CrmContact> {

    @Autowired
    private CrmContactService crmContactService;

    @Autowired
    private CrmCustomerService crmCustomerService;

    @Autowired
    private CrmContactLinkService crmContactLinkService;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Override
    protected void setService() {
        this.service = crmContactService;
    }

    // --- 查询 ----------------------------------------------------------

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "条件") @RequestParam(value = "search", defaultValue = "") String search,
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "页大小") @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        IPage<CrmContactListVo> list = crmContactService.queryPage(search, "", page, size);
        if (list.getRecords().size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public Object detail(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        CrmContactDetailVo crmContactDetailVo = crmContactService.queryDetail(id);
        if (!ObjectUtils.isEmpty(crmContactDetailVo)) {
            crmContactDetailVo.setLinkList(crmContactLinkService.list(new QueryWrapper<CrmContactLink>()
                    .eq(CrmContactLink.CONTACT_ID, crmContactDetailVo.getId())));
            return ResultFormat.success("查询成功", crmContactDetailVo);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "客户")
    @RequestMapping(value = "customer", method = RequestMethod.GET)
    public Object customer(
            @ApiParam(value = "条件") @RequestParam(value = "search", defaultValue = "") String search,
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        QueryWrapper<CrmCustomer> query = new QueryWrapper<>();
        if (StringUtils.hasLength(search)) {
            query.eq(CrmCustomer.SERIAL, search)
                    .or()
                    .eq(CrmCustomer.FULL_NAME, search);
        }
        Page<CrmCustomer> list = crmCustomerService.page(new Page<>(page, this.pageSize), query);
        if (list.getRecords().size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    // --- 新增 ----------------------------------------------------------

    @ApiOperation(value = "新增")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "归属用户ID") @RequestParam(value = "userId") String userId,
            @ApiParam(value = "编号") @RequestParam(value = "serial") String serial,
            @ApiParam(value = "联系人姓名") @RequestParam(value = "name") String name,
            @ApiParam(value = "管理客户") @RequestParam(value = "customerId", defaultValue = "") String customerId,
            @ApiParam(value = "性别[0 未知 | 1 男 | 2 女]") @RequestParam(value = "gender") Integer gender,
            @ApiParam(value = "岗位") @RequestParam(value = "position", defaultValue = "") String position,
            @ApiParam(value = "部门") @RequestParam(value = "department", defaultValue = "") String department,
            @ApiParam(value = "角色关系[0 经办人 | 1 决策人 | 2 使用人 | 3 意见影响人 | 4 商务决策]") @RequestParam(value = "role", defaultValue = "") Integer role,
            @ApiParam(value = "亲密[0 初相识 | 1 一般关系 | 2 朋友关系 | 3 好友关系]") @RequestParam(value = "relation", defaultValue = "") Integer relation,
            @ApiParam(value = "生日") @RequestParam(value = "birthday", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday,
            @ApiParam(value = "爱好") @RequestParam(value = "hobby", defaultValue = "") String hobby,
            @ApiParam(value = "备注") @RequestParam(value = "remark", defaultValue = "") String remark,
            @ApiParam(value = "联系方式") @RequestParam(value = "link", defaultValue = "") String link
    ) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            CrmContact crmContact = new CrmContact();
            crmContact.setUserId(userId);
            crmContact.setAssignUserId(LoginUser.current().id);
            crmContact.setAssignTime(SQLDatetime.time(DateUtil.date()));
            crmContact.setCreatorUserId(LoginUser.current().id);
            crmContact.setSerial(serial);
            crmContact.setName(name);
            crmContact.setCustomerId(customerId);
            crmContact.setInitial(String.valueOf(PinyinUtil.getFirstLetter(name.charAt(0))).toUpperCase());
            crmContact.setGender(gender);
            crmContact.setPosition(position);
            crmContact.setDepartment(department);
            if (!ObjectUtils.isEmpty(role)) {
                crmContact.setRole(role);
            }
            if (!ObjectUtils.isEmpty(relation)) {
                crmContact.setRelation(relation);
            }
            if (!ObjectUtils.isEmpty(birthday)) {
                crmContact.setBirthday(SQLDatetime.date(DateUtil.date(birthday)));
            }
            crmContact.setHobby(hobby);
            crmContact.setRemark(remark);
            crmContact.setCreateTime(SQLDatetime.time(DateUtil.date()));
            crmContactService.save(crmContact);

            List<CrmContactLink> list = GsonUtils.toList(link, CrmContactLink.class);
            list.forEach(item -> item.setContactId(crmContact.getId()));
            crmContactLinkService.saveBatch(list);

            transactionManager.commit(status);
            return ResultFormat.success("新增成功", "");
        } catch (Exception e) {
            transactionManager.rollback(status);
            return ResultFormat.error("新增失败");
        }
    }

    // --- 编辑 ----------------------------------------------------------

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id,
            @ApiParam(value = "归属用户ID") @RequestParam(value = "userId") String userId,
            @ApiParam(value = "编号") @RequestParam(value = "serial") String serial,
            @ApiParam(value = "联系人姓名") @RequestParam(value = "name") String name,
            @ApiParam(value = "管理客户") @RequestParam(value = "customerId", defaultValue = "") String customerId,
            @ApiParam(value = "性别[0 未知 | 1 男 | 2 女]") @RequestParam(value = "gender") Integer gender,
            @ApiParam(value = "岗位") @RequestParam(value = "position", defaultValue = "") String position,
            @ApiParam(value = "部门") @RequestParam(value = "department", defaultValue = "") String department,
            @ApiParam(value = "角色关系[0 经办人 | 1 决策人 | 2 使用人 | 3 意见影响人 | 4 商务决策]") @RequestParam(value = "role", defaultValue = "") Integer role,
            @ApiParam(value = "亲密[0 初相识 | 1 一般关系 | 2 朋友关系 | 3 好友关系]") @RequestParam(value = "relation", defaultValue = "") Integer relation,
            @ApiParam(value = "生日") @RequestParam(value = "birthday", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday,
            @ApiParam(value = "爱好") @RequestParam(value = "hobby", defaultValue = "") String hobby,
            @ApiParam(value = "备注") @RequestParam(value = "remark", defaultValue = "") String remark,
            @ApiParam(value = "联系方式") @RequestParam(value = "link", defaultValue = "") String link
    ) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            CrmContact crmContact = new CrmContact();
            crmContact.setUserId(userId);
            crmContact.setAssignUserId(LoginUser.current().id);
            crmContact.setAssignTime(SQLDatetime.time(DateUtil.date()));
            crmContact.setCreatorUserId(LoginUser.current().id);
            crmContact.setSerial(serial);
            crmContact.setName(name);
            crmContact.setCustomerId(customerId);
            crmContact.setInitial(String.valueOf(PinyinUtil.getFirstLetter(name.charAt(0))).toUpperCase());
            crmContact.setGender(gender);
            crmContact.setPosition(position);
            crmContact.setDepartment(department);
            if (!ObjectUtils.isEmpty(role)) {
                crmContact.setRole(role);
            }
            if (!ObjectUtils.isEmpty(relation)) {
                crmContact.setRelation(relation);
            }
            if (!ObjectUtils.isEmpty(birthday)) {
                crmContact.setBirthday(SQLDatetime.date(DateUtil.date(birthday)));
            }
            crmContact.setHobby(hobby);
            crmContact.setRemark(remark);
            crmContact.setUpdateTime(SQLDatetime.time(DateUtil.date()));
            crmContactService.update(crmContact, new QueryWrapper<CrmContact>().eq(CrmContact.ID, id));

            crmContactLinkService.remove(new QueryWrapper<CrmContactLink>().eq(CrmContactLink.CONTACT_ID, id));
            List<CrmContactLink> list = GsonUtils.toList(link, CrmContactLink.class);
            list.forEach(item -> item.setContactId(id));
            crmContactLinkService.saveBatch(list);

            transactionManager.commit(status);
            return ResultFormat.success("编辑成功", "");
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            return ResultFormat.error("编辑失败");
        }
    }

    // --- 删除 ----------------------------------------------------------

    @ApiOperation(value = "删除")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Object delete(
            @ApiParam(value = "主键") @RequestParam(value = "id") String id
    ) {
        boolean result = crmContactService.removeById(id);
        if (result) {
            crmContactLinkService.remove(new QueryWrapper<CrmContactLink>().eq(CrmContactLink.CONTACT_ID, id));
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除失败");
    }

}