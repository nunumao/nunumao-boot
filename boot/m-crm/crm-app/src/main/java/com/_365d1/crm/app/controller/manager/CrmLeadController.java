package com._365d1.crm.app.controller.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/10/16
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import com._365d1.crm.app.controller.Manager;
import com._365d1.crm.model.CrmLead;
import com._365d1.crm.service.CrmLeadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "线索管理")
@RestController(value = Manager.CRTL_PREFIX + "CrmLeadController")
@RequestMapping(value = "lead")
public class CrmLeadController extends Manager<CrmLeadService, CrmLead> {

    @Autowired
    private CrmLeadService crmLeadService;

    @Override
    protected void setService() {
        this.service = crmLeadService;
    }

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "状态") @RequestParam(value = "status", defaultValue = "0") Integer status,
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam(value = "页大小") @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return this._list(query -> {
            if (!status.equals(0)) {
                query.eq(CrmLead.STATUS, status);
            }
        }, page, size);
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public Object detail(String id) {
        return this._detail(id);
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "客户名称") @RequestParam(value = "contact") String contact,
            @ApiParam(value = "客户公司") @RequestParam(value = "customer") String customer,
            @ApiParam(value = "客户手机") @RequestParam(value = "phone", defaultValue = "") String phone,
            @ApiParam(value = "办公电话") @RequestParam(value = "telephone", defaultValue = "") String telephone,
            @ApiParam(value = "客户邮箱") @RequestParam(value = "email", defaultValue = "") String email,
            @ApiParam(value = "客户地址") @RequestParam(value = "address", defaultValue = "") String address,
            @ApiParam(value = "备注") @RequestParam(value = "remark", defaultValue = "") String remark,
            @ApiParam(value = "客户状态") @RequestParam(value = "status", defaultValue = "0") Integer status
    ) {
        return this._add(new CrmLead() {{
            setContact(contact);
            setCustomer(customer);
            setPhone(phone);
            setTelephone(telephone);
            setEmail(email);
            setAddress(address);
            setRemark(remark);
            setStatus(status);
        }});
    }

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id,
            @ApiParam(value = "客户名称") @RequestParam(value = "contact") String contact,
            @ApiParam(value = "客户公司") @RequestParam(value = "customer") String customer,
            @ApiParam(value = "客户手机") @RequestParam(value = "phone", defaultValue = "") String phone,
            @ApiParam(value = "办公电话") @RequestParam(value = "telephone", defaultValue = "") String telephone,
            @ApiParam(value = "客户邮箱") @RequestParam(value = "email", defaultValue = "") String email,
            @ApiParam(value = "客户地址") @RequestParam(value = "address", defaultValue = "") String address,
            @ApiParam(value = "备注") @RequestParam(value = "remark", defaultValue = "") String remark,
            @ApiParam(value = "客户状态") @RequestParam(value = "status", defaultValue = "0") Integer status
    ) {
        return this._edit(new CrmLead() {{
            setContact(contact);
            setCustomer(customer);
            setPhone(phone);
            setTelephone(telephone);
            setEmail(email);
            setAddress(address);
            setRemark(remark);
            setStatus(status);
        }}, id);
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Object delete(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        return this._delete(id.split(","));
    }

}
