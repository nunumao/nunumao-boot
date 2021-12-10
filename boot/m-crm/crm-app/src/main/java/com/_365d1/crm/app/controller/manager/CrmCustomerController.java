package com._365d1.crm.app.controller.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/10/19
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.crm.app.controller.Manager;
import com._365d1.crm.model.CrmCustomer;
import com._365d1.crm.model.enums.CrmCustomerLevel;
import com._365d1.crm.model.enums.CrmCustomerStatus;
import com._365d1.crm.service.CrmCustomerService;
import io.github.nunumao.common.utils.ResultFormat;
import io.github.nunumao.common.utils.SQLDatetime;
import io.github.nunumao.common.utils.enums.Dictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "客户管理")
@RestController(value = Manager.CRTL_PREFIX + "CrmCustomerController")
@RequestMapping(value = "customer")
public class CrmCustomerController extends Manager<CrmCustomerService, CrmCustomer> {

    @Autowired
    private CrmCustomerService crmCustomerService;

    @Override
    protected void setService() {
        this.service = crmCustomerService;
    }

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "页码") @RequestParam(value = "page") Integer page,
            @ApiParam(value = "页大小") @RequestParam(value = "size") Integer size
    ) {
        return this._list(query -> {

        }, page, size);
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public Object detail(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        return this._detail(id);
    }

    @ApiOperation(value = "客户分级")
    @RequestMapping(value = "level", method = RequestMethod.GET)
    public Object level() {
        return ResultFormat.success("查询成功", Dictionary.list(CrmCustomerLevel.values()));
    }

    @ApiOperation(value = "客户状态")
    @RequestMapping(value = "status", method = RequestMethod.GET)
    public Object status() {
        return ResultFormat.success("查询成功", Dictionary.list(CrmCustomerStatus.values()));
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "客户全称") @RequestParam(value = "fullName") String fullName,
            @ApiParam(value = "客户编号") @RequestParam(value = "serial") String serial,
            @ApiParam(value = "客户分级") @RequestParam(value = "level") Integer level,
            @ApiParam(value = "客户状态") @RequestParam(value = "status") Integer status,
            @ApiParam(value = "客户简称") @RequestParam(value = "shortName", defaultValue = "") String shortName,
            @ApiParam(value = "客户简介") @RequestParam(value = "intro", defaultValue = "") String intro,
            @ApiParam(value = "客户手机") @RequestParam(value = "phone", defaultValue = "") String phone,
            @ApiParam(value = "客户地址") @RequestParam(value = "address", defaultValue = "") String address,
            @ApiParam(value = "官方网址") @RequestParam(value = "website", defaultValue = "") String website,
            @ApiParam(value = "邮政编码") @RequestParam(value = "zipcode", defaultValue = "") String zipcode
    ) {
        return this._add(new CrmCustomer() {{
            setFullName(fullName);
            setShortName(shortName);
            setSerial(serial);
            setLevel(level);
            setStatus(status);
            setIntro(intro);
            setPhone(phone);
            setAddress(address);
            setZipcode(zipcode);
            setWebsite(website);
            setCreateTime(SQLDatetime.time());
        }});
    }

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id,
            @ApiParam(value = "客户全称") @RequestParam(value = "fullName") String fullName,
            @ApiParam(value = "客户编号") @RequestParam(value = "serial") String serial,
            @ApiParam(value = "客户分级") @RequestParam(value = "level") Integer level,
            @ApiParam(value = "客户状态") @RequestParam(value = "status") Integer status,
            @ApiParam(value = "客户简称") @RequestParam(value = "shortName", defaultValue = "") String shortName,
            @ApiParam(value = "客户简介") @RequestParam(value = "intro", defaultValue = "") String intro,
            @ApiParam(value = "客户手机") @RequestParam(value = "phone", defaultValue = "") String phone,
            @ApiParam(value = "客户地址") @RequestParam(value = "address", defaultValue = "") String address,
            @ApiParam(value = "官方网址") @RequestParam(value = "website", defaultValue = "") String website,
            @ApiParam(value = "邮政编码") @RequestParam(value = "zipcode", defaultValue = "") String zipcode
    ) {
        return this._edit(new CrmCustomer() {{
            setFullName(fullName);
            setShortName(shortName);
            setSerial(serial);
            setLevel(level);
            setStatus(status);
            setIntro(intro);
            setPhone(phone);
            setAddress(address);
            setZipcode(zipcode);
            setWebsite(website);
            setCreateTime(SQLDatetime.time());
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
