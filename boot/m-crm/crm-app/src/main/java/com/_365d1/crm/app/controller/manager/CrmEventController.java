package com._365d1.crm.app.controller.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: Nunumao 代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-20 12:29
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import cn.hutool.core.date.DateUtil;
import com._365d1.crm.app.controller.Manager;
import com._365d1.crm.model.CrmEvent;
import com._365d1.crm.service.CrmEventService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.nunumao.common.utils.ResultFormat;
import io.github.nunumao.common.utils.SQLDatetime;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@Api(tags = "事件管理")
@RestController(value = Manager.CRTL_PREFIX + "crmEventController")
@RequestMapping(value = "event")
public class CrmEventController extends Manager<CrmEventService, CrmEvent> {

    @Autowired
    private CrmEventService crmEventService;

    // --- 查询 ----------------------------------------------------------

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "页大小") @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        IPage<CrmEvent> list = crmEventService.page(new Page<>(page, size));
        if (list.getRecords().size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    // --- 新增 ----------------------------------------------------------

    @ApiOperation(value = "新增")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "所属用户") @RequestParam(value = "userId") String userId,
            @ApiParam(value = "编辑用户") @RequestParam(value = "editorUserId") String editorUserId,
            @ApiParam(value = "事件内容") @RequestParam(value = "content") String content,
            @ApiParam(value = "所属客户ID") @RequestParam(value = "customerId") String customerId,
            @ApiParam(value = "所属联系人ID") @RequestParam(value = "contactId") String contactId,
            @ApiParam(value = "所属商机ID") @RequestParam(value = "chanceId") String chanceId,
            @ApiParam(value = "事件地点[0 电话联系 | 1 线上沟通 | 2 邮件沟通 | 3 上门洽谈 | 4 公司会谈]") @RequestParam(value = "location") Integer location,
            @ApiParam(value = "事件结果[0 客户不满意 | 1 客户满意 | 2 客户无表示 | 3 未联系上客户]") @RequestParam(value = "result") Integer result,
            @ApiParam(value = "事件日期") @RequestParam(value = "eventDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date eventDate,
            @ApiParam(value = "创建时间") @RequestParam(value = "createTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date createTime,
            @ApiParam(value = "更新时间") @RequestParam(value = "updateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date updateTime,
            @ApiParam(value = "状态") @RequestParam(value = "status") Integer status
    ) {
        CrmEvent crmEvent = new CrmEvent();
        crmEvent.setUserId(userId);
        crmEvent.setEditorUserId(editorUserId);
        crmEvent.setContent(content);
        crmEvent.setCustomerId(customerId);
        crmEvent.setContactId(contactId);
        crmEvent.setChanceId(chanceId);
        crmEvent.setLocation(location);
        crmEvent.setResult(result);
        crmEvent.setEventDate(SQLDatetime.date(DateUtil.date(eventDate)));
        crmEvent.setCreateTime(SQLDatetime.time(DateUtil.date(createTime)));
        crmEvent.setUpdateTime(SQLDatetime.time(DateUtil.date(updateTime)));
        crmEvent.setStatus(status);

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
            @ApiParam(value = "所属用户") @RequestParam(value = "userId") String userId,
            @ApiParam(value = "编辑用户") @RequestParam(value = "editorUserId") String editorUserId,
            @ApiParam(value = "事件内容") @RequestParam(value = "content") String content,
            @ApiParam(value = "所属客户ID") @RequestParam(value = "customerId") String customerId,
            @ApiParam(value = "所属联系人ID") @RequestParam(value = "contactId") String contactId,
            @ApiParam(value = "所属商机ID") @RequestParam(value = "chanceId") String chanceId,
            @ApiParam(value = "事件地点[0 电话联系 | 1 线上沟通 | 2 邮件沟通 | 3 上门洽谈 | 4 公司会谈]") @RequestParam(value = "location") Integer location,
            @ApiParam(value = "事件结果[0 客户不满意 | 1 客户满意 | 2 客户无表示 | 3 未联系上客户]") @RequestParam(value = "result") Integer result,
            @ApiParam(value = "事件日期") @RequestParam(value = "eventDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date eventDate,
            @ApiParam(value = "创建时间") @RequestParam(value = "createTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date createTime,
            @ApiParam(value = "更新时间") @RequestParam(value = "updateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date updateTime,
            @ApiParam(value = "状态") @RequestParam(value = "status") Integer status
    ) {
        CrmEvent crmEvent = new CrmEvent();
        crmEvent.setUserId(userId);
        crmEvent.setEditorUserId(editorUserId);
        crmEvent.setContent(content);
        crmEvent.setCustomerId(customerId);
        crmEvent.setContactId(contactId);
        crmEvent.setChanceId(chanceId);
        crmEvent.setLocation(location);
        crmEvent.setResult(result);
        crmEvent.setEventDate(SQLDatetime.date(DateUtil.date(eventDate)));
        crmEvent.setCreateTime(SQLDatetime.time(DateUtil.date(createTime)));
        crmEvent.setUpdateTime(SQLDatetime.time(DateUtil.date(updateTime)));
        crmEvent.setStatus(status);

        boolean flag = crmEventService.update(crmEvent, new QueryWrapper<CrmEvent>().eq(CrmEvent.ID, id));
        if (flag) {
            return ResultFormat.success("编辑成功", "");
        }
        return ResultFormat.error("编辑失败");
    }

    // --- 删除 ----------------------------------------------------------

    @ApiOperation(value = "删除")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Object delete(
            @ApiParam(value = "主键") @RequestParam(value = "id") String id
    ) {
        boolean result = crmEventService.removeById(id);
        if (result) {
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除失败");
    }

}