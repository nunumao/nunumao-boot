package com._365d1.crm.app.controller.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: Nunumao 代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-12-09 23:22
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.crm.app.controller.Manager;
import com._365d1.crm.model.CrmCase;
import com._365d1.crm.service.CrmCaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.nunumao.common.utils.ResultFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "方案管理")
@RestController(value = Manager.CRTL_PREFIX + "crmCaseController")
@RequestMapping(value = "case")
public class CrmCaseController extends Manager<CrmCaseService, CrmCase> {

    @Autowired
    private CrmCaseService crmCaseService;

    // --- 查询 ----------------------------------------------------------

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "页大小") @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        IPage<CrmCase> list = crmCaseService.page(new Page<>(page, size));
        if (list.getRecords().size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("查询失败");
    }

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        CrmCase crmCase = crmCaseService.getById(id);
        if (!ObjectUtils.isEmpty(crmCase)) {
            return ResultFormat.success("查询成功", crmCase);
        }
        return ResultFormat.error("没有数据");
    }

    // --- 新增 ----------------------------------------------------------

    @ApiOperation(value = "新增")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "方案名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "方案内容") @RequestParam(value = "content") String content
    ) {
        CrmCase crmCase = new CrmCase();
        crmCase.setName(name);
        crmCase.setContent(content);

        boolean flag = crmCaseService.save(crmCase);
        if (flag) {
            return ResultFormat.success("新增成功", crmCase.getId());
        }
        return ResultFormat.error("新增失败");
    }

    // --- 编辑 ----------------------------------------------------------

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id,
            @ApiParam(value = "方案名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "方案内容") @RequestParam(value = "content") String content
    ) {
        CrmCase crmCase = new CrmCase();
        crmCase.setName(name);
        crmCase.setContent(content);

        boolean flag = crmCaseService.update(crmCase, new QueryWrapper<CrmCase>().eq(CrmCase.ID, id));
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
        boolean result = crmCaseService.removeById(id);
        if (result) {
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除失败");
    }

}