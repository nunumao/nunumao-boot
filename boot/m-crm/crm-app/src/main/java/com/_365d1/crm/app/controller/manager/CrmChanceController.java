package com._365d1.crm.app.controller.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: Nunumao 代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-15 15:25
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import cn.hutool.core.date.DateUtil;
import com._365d1.crm.app.controller.Manager;
import com._365d1.crm.model.*;
import com._365d1.crm.model.vo.CrmChanceDetailVo;
import com._365d1.crm.model.vo.CrmChanceListVo;
import com._365d1.crm.model.vo.CrmContactListVo;
import com._365d1.crm.service.*;
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
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Api(tags = "商机管理")
@RestController(value = Manager.CRTL_PREFIX + "crmChanceController")
@RequestMapping(value = "chance")
public class CrmChanceController extends Manager<CrmChanceService, CrmChance> {

    @Autowired
    private CrmChanceService crmChanceService;

    @Autowired
    private CrmCustomerService crmCustomerService;

    @Autowired
    private CrmContactService crmContactService;

    @Autowired
    private CrmChanceConditionService crmChanceConditionService;

    @Autowired
    private CrmEventService crmEventService;

    @Override
    protected void setService() {
        this.service = crmChanceService;
    }

    // --- 查询 ----------------------------------------------------------

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "条件") @RequestParam(value = "search", defaultValue = "") String search,
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "页大小") @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        IPage<CrmChanceListVo> list = crmChanceService.queryPage(search, page, size);
        if (list.getRecords().size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "签单条件列表")
    @RequestMapping(value = "signList", method = RequestMethod.GET)
    public Object signList(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        List<CrmChanceCondition> list = crmChanceConditionService.list(new QueryWrapper<CrmChanceCondition>()
                .eq(CrmChanceCondition.CHANCE_ID, id));
        if (list.size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "编辑数据")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        CrmChanceDetailVo crmChanceDetailVo = crmChanceService.queryDetail(id);
        if (!ObjectUtils.isEmpty(crmChanceDetailVo)) {
            return ResultFormat.success("查询成功", crmChanceDetailVo);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public Object detail(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        CrmChanceDetailVo crmChanceDetailVo = crmChanceService.queryDetail(id);
        if (!ObjectUtils.isEmpty(crmChanceDetailVo)) {
            return ResultFormat.success("查询成功", crmChanceDetailVo);
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
        IPage<CrmCustomer> list = crmCustomerService.page(new Page<>(page, this.pageSize), query);
        if (list.getRecords().size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "联系人")
    @RequestMapping(value = "contact", method = RequestMethod.GET)
    public Object contact(
            @ApiParam(value = "操作") @RequestParam(value = "action", defaultValue = "0") Integer action,
            @ApiParam(value = "条件") @RequestParam(value = "search", defaultValue = "") String search,
            @ApiParam(value = "客户") @RequestParam(value = "customer", defaultValue = "") String customer,
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page
    ) {
        if (!StringUtils.hasLength(customer)) {
            return ResultFormat.error("没有数据");
        }
        if (action.equals(1)) {
            IPage<CrmContactListVo> list = crmContactService.queryPage(search, customer, page, this.pageSize);
            if (list.getRecords().size() > 0) {
                return ResultFormat.success("查询成功", list);
            }
        } else {
            List<CrmContact> list = crmContactService.list(new QueryWrapper<CrmContact>().eq(CrmContact.CUSTOMER_ID, customer));
            if (list.size() > 0) {
                return ResultFormat.success("查询成功", list);
            }
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "事件列表")
    @RequestMapping(value = "eventList", method = RequestMethod.GET)
    public Object eventList(
            @ApiParam(value = "所属商机ID") @RequestParam(value = "chanceId") String chanceId
    ) {
        List<CrmEvent> list = crmEventService.list(new QueryWrapper<CrmEvent>().eq(CrmEvent.CHANCE_ID, chanceId));
        if (list.size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "编辑事件")
    @RequestMapping(value = "editEevent", method = RequestMethod.GET)
    public Object editEevent(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        CrmEvent crmEvent = crmEventService.getById(id);
        if (!ObjectUtils.isEmpty(crmEvent)) {
            return ResultFormat.success("查询成功", crmEvent);
        }
        return ResultFormat.error("没有数据");
    }

    // --- 新增 ----------------------------------------------------------

    @ApiOperation(value = "新增")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "所属用户ID") @RequestParam(value = "userId") String userId,
            @ApiParam(value = "商机编号") @RequestParam(value = "serial") String serial,
            @ApiParam(value = "商机名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "关联客户") @RequestParam(value = "customerId", defaultValue = "") String customerId,
            @ApiParam(value = "关联联系人") @RequestParam(value = "contactId", defaultValue = "") String contactId,
            @ApiParam(value = "预期收入") @RequestParam(value = "expectIncome", defaultValue = "0") Double expectIncome,
            @ApiParam(value = "预期时间") @RequestParam(value = "expectDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date expectDate,
            @ApiParam(value = "成交机率") @RequestParam(value = "probability", defaultValue = "") Integer probability,
            @ApiParam(value = "商机进度") @RequestParam(value = "progress") Integer progress,
            @ApiParam(value = "备注") @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        CrmChance crmChance = new CrmChance();
        crmChance.setUserId(userId);
        crmChance.setAssignUserId(LoginUser.current().id);
        crmChance.setAssignTime(SQLDatetime.time());
        crmChance.setCreatorUserId(LoginUser.current().id);
        crmChance.setSerial(serial);
        crmChance.setName(name);
        crmChance.setCustomerId(customerId);
        crmChance.setContactId(contactId);
        crmChance.setExpectIncome(new BigDecimal(expectIncome));
        if (!ObjectUtils.isEmpty(expectDate)) {
            crmChance.setExpectDate(SQLDatetime.date(DateUtil.date(expectDate)));
        }
        crmChance.setProbability(probability);
        crmChance.setProgress(progress);
        crmChance.setRemark(remark);
        crmChance.setCreateTime(SQLDatetime.time());

        return this._add(crmChance);

    }

    @ApiOperation(value = "添加签单条件")
    @RequestMapping(value = "addSign", method = RequestMethod.POST)
    public Object addSign(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id,
            @ApiParam(value = "条件") @RequestParam(value = "condition") String condition
    ) {
        List<CrmChanceCondition> list = GsonUtils.toList(condition, CrmChanceCondition.class);
        list.forEach(item -> item.setChanceId(id));
        boolean flag = crmChanceConditionService.saveBatch(list);
        if (flag) {
            return ResultFormat.success("新增成功", "");
        }
        return ResultFormat.error("新增失败");
    }

    @ApiOperation(value = "新增事件")
    @RequestMapping(value = "addEvent", method = RequestMethod.POST)
    public Object addEvent(
            @ApiParam(value = "事件内容") @RequestParam(value = "content") String content,
            @ApiParam(value = "所属商机ID") @RequestParam(value = "chanceId") String chanceId,
            @ApiParam(value = "客户ID") @RequestParam(value = "customerId", defaultValue = "") String customerId,
            @ApiParam(value = "联系人ID") @RequestParam(value = "contactId", defaultValue = "") String contactId,
            @ApiParam(value = "事件地点[0 电话联系 | 1 线上沟通 | 2 邮件沟通 | 3 上门洽谈 | 4 公司会谈]") @RequestParam(value = "location") Integer location,
            @ApiParam(value = "事件结果[0 客户不满意 | 1 客户满意 | 2 客户无表示 | 3 未联系上客户]") @RequestParam(value = "result") Integer result,
            @ApiParam(value = "事件日期") @RequestParam(value = "eventDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date eventDate
    ) {

        CrmEvent crmEvent = new CrmEvent();
        if (StringUtils.hasLength(customerId) && StringUtils.hasLength(contactId)) {
            crmEvent.setCustomerId(customerId);
            crmEvent.setContactId(contactId);
        }
        crmEvent.setUserId(LoginUser.current().id);
        crmEvent.setContent(content);
        crmEvent.setChanceId(chanceId);
        crmEvent.setLocation(location);
        crmEvent.setResult(result);
        crmEvent.setEventDate(SQLDatetime.date(DateUtil.date(eventDate)));
        crmEvent.setCreateTime(SQLDatetime.time(DateUtil.date()));

        boolean flag = crmEventService.save(crmEvent);
        if (flag) {
            return ResultFormat.success("新增成功", crmEvent.getId());
        }
        return ResultFormat.error("新增失败");
    }

    // --- 编辑 ----------------------------------------------------------

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id,
            @ApiParam(value = "所属用户ID") @RequestParam(value = "userId") String userId,
            @ApiParam(value = "商机编号") @RequestParam(value = "serial") String serial,
            @ApiParam(value = "商机名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "关联客户") @RequestParam(value = "customerId", defaultValue = "") String customerId,
            @ApiParam(value = "关联联系人") @RequestParam(value = "contactId", defaultValue = "") String contactId,
            @ApiParam(value = "预期收入") @RequestParam(value = "expectIncome", defaultValue = "0") Double expectIncome,
            @ApiParam(value = "预期时间") @RequestParam(value = "expectDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date expectDate,
            @ApiParam(value = "成交机率") @RequestParam(value = "probability", defaultValue = "") Integer probability,
            @ApiParam(value = "商机进度") @RequestParam(value = "progress") Integer progress,
            @ApiParam(value = "备注") @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        CrmChance crmChance = new CrmChance();
        crmChance.setUserId(userId);
        crmChance.setSerial(serial);
        crmChance.setName(name);
        crmChance.setCustomerId(customerId);
        crmChance.setContactId(contactId);
        crmChance.setExpectIncome(new BigDecimal(expectIncome));
        crmChance.setExpectDate(SQLDatetime.date(DateUtil.date(expectDate)));
        crmChance.setProbability(probability);
        crmChance.setProgress(progress);
        crmChance.setUpdateTime(SQLDatetime.time());
        crmChance.setRemark(remark);

        boolean flag = crmChanceService.update(crmChance, new QueryWrapper<CrmChance>().eq(CrmChance.ID, id));
        if (flag) {
            return ResultFormat.success("编辑成功", "");
        }
        return ResultFormat.error("编辑失败");
    }

    @ApiOperation(value = "编辑签单条件")
    @RequestMapping(value = "editDetail", method = RequestMethod.PUT)
    public Object editDetail(
            @ApiParam(value = "主键") @RequestParam(value = "id") String id,
            @ApiParam(value = "成交机率") @RequestParam(value = "probability", defaultValue = "") Integer probability,
            @ApiParam(value = "商机进度") @RequestParam(value = "progress", defaultValue = "") Integer progress
    ) {
        CrmChance crmChance = new CrmChance();
        if (!ObjectUtils.isEmpty(probability)) {
            crmChance.setProbability(probability);
        } else if (!ObjectUtils.isEmpty(progress)) {
            crmChance.setProgress(progress);
        } else {
            return ResultFormat.error("编辑失败");
        }
        boolean result = crmChanceService.update(crmChance, new QueryWrapper<CrmChance>().eq(CrmChance.ID, id));
        if (result) {
            return ResultFormat.success("编辑成功", "");
        }
        return ResultFormat.error("编辑失败");
    }

    @ApiOperation(value = "编辑签单条件")
    @RequestMapping(value = "editSign", method = RequestMethod.PUT)
    public Object editSign(
            @ApiParam(value = "主键") @RequestParam(value = "id") String id,
            @ApiParam(value = "状态") @RequestParam(value = "status") Integer status
    ) {
        boolean result = crmChanceConditionService.update(new CrmChanceCondition() {{
            setStatus(status);
        }}, new QueryWrapper<CrmChanceCondition>().eq(CrmChanceCondition.ID, id));
        if (result) {
            return ResultFormat.success("编辑成功", "");
        }
        return ResultFormat.error("编辑失败");
    }

    @ApiOperation(value = "编辑事件")
    @RequestMapping(value = "editEvent", method = RequestMethod.PUT)
    public Object editEvent(
            @ApiParam(value = "主键") @RequestParam(value = "id") String id,
            @ApiParam(value = "事件内容") @RequestParam(value = "content") String content,
            @ApiParam(value = "所属商机ID") @RequestParam(value = "chanceId") String chanceId,
            @ApiParam(value = "客户ID") @RequestParam(value = "customerId", defaultValue = "") String customerId,
            @ApiParam(value = "联系人ID") @RequestParam(value = "contactId", defaultValue = "") String contactId,
            @ApiParam(value = "事件地点[0 电话联系 | 1 线上沟通 | 2 邮件沟通 | 3 上门洽谈 | 4 公司会谈]") @RequestParam(value = "location") Integer location,
            @ApiParam(value = "事件结果[0 客户不满意 | 1 客户满意 | 2 客户无表示 | 3 未联系上客户]") @RequestParam(value = "result") Integer result,
            @ApiParam(value = "事件日期") @RequestParam(value = "eventDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date eventDate
    ) {

        CrmEvent crmEvent = new CrmEvent();
        if (StringUtils.hasLength(customerId) && StringUtils.hasLength(contactId)) {
            crmEvent.setCustomerId(customerId);
            crmEvent.setContactId(contactId);
        }
        crmEvent.setEditorUserId(LoginUser.current().id);
        crmEvent.setContent(content);
        crmEvent.setChanceId(chanceId);
        crmEvent.setLocation(location);
        crmEvent.setResult(result);
        crmEvent.setEventDate(SQLDatetime.date(DateUtil.date(eventDate)));
        crmEvent.setUpdateTime(SQLDatetime.time(DateUtil.date()));

        boolean flag = crmEventService.update(crmEvent, new QueryWrapper<CrmEvent>().eq(CrmEvent.ID, id));
        if (flag) {
            return ResultFormat.success("编辑成功", crmEvent.getId());
        }
        return ResultFormat.error("编辑失败");
    }

    // --- 删除 ----------------------------------------------------------

    @ApiOperation(value = "删除")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Object delete(
            @ApiParam(value = "主键") @RequestParam(value = "id") String id
    ) {
        boolean result = crmChanceService.removeById(id);
        if (result) {
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除失败");
    }

    @ApiOperation(value = "删除签单条件")
    @RequestMapping(value = "deleteSign", method = RequestMethod.DELETE)
    public Object deleteSign(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        boolean result = crmChanceConditionService.removeById(id);
        if (result) {
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除成功");
    }

}