package com._365d1.crm.app.controller.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: Nunumao 代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-23 09:22
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.crm.app.controller.Manager;
import com._365d1.crm.model.CrmProduct;
import com._365d1.crm.model.CrmProductCategory;
import com._365d1.crm.model.vo.CrmProductVo;
import com._365d1.crm.service.CrmProductCategoryService;
import com._365d1.crm.service.CrmProductService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.nunumao.common.utils.ResultFormat;
import io.github.nunumao.common.utils.SQLDatetime;
import io.github.nunumao.common.utils.tree.TreeParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;


@Api(tags = "产品管理")
@RestController(value = Manager.CRTL_PREFIX + "crmProductController")
@RequestMapping(value = "product")
public class CrmProductController extends Manager<CrmProductService, CrmProduct> {

    @Autowired
    private CrmProductCategoryService crmProductCategoryService;

    @Autowired
    private CrmProductService crmProductService;

    // --- 查询 ----------------------------------------------------------

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "条件") @RequestParam(value = "search", defaultValue = "") String search,
            @ApiParam(value = "分类") @RequestParam(value = "category", defaultValue = "") String category,
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam(value = "页大小") @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        IPage<CrmProductVo> list = crmProductService.queryPage(search, category, page, size);
        if (list.getRecords().size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        CrmProductVo crmProductVo = crmProductService.queryDetail(id);
        if (!ObjectUtils.isEmpty(crmProductVo)) {
            return ResultFormat.success("查询成功", crmProductVo);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "分类列表")
    @RequestMapping(value = "categoryList", method = RequestMethod.GET)
    public Object categoryList() {
        List<CrmProductCategory> list = crmProductCategoryService.list();
        if (list.size() > 0) {
            list = TreeParser.listToTree("0", list);
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "上级分类")
    @RequestMapping(value = "parent", method = RequestMethod.GET)
    public Object parent() {
        List<CrmProductCategory> list = crmProductCategoryService.list();
        if (list.size() > 0) {
            list = TreeParser.listToTree("0", list);
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "分类编辑")
    @RequestMapping(value = "editCategory", method = RequestMethod.GET)
    public Object editCategory(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        CrmProductCategory crmProductCategory = crmProductCategoryService.getById(id);
        if (!ObjectUtils.isEmpty(crmProductCategory)) {
            return ResultFormat.success("查询成功", crmProductCategory);
        }
        return ResultFormat.error("没有数据");
    }

    // --- 新增 ----------------------------------------------------------

    @ApiOperation(value = "新增")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "所属分类") @RequestParam(value = "categoryId") String categoryId,
            @ApiParam(value = "产品编号") @RequestParam(value = "serial") String serial,
            @ApiParam(value = "产品名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "成本价") @RequestParam(value = "costPrice") Double costPrice,
            @ApiParam(value = "销售价") @RequestParam(value = "sellingPrice") Double sellingPrice,
            @ApiParam(value = "单位") @RequestParam(value = "unit") String unit,
            @ApiParam(value = "状态") @RequestParam(value = "status", defaultValue = "1") Integer status,
            @ApiParam(value = "备注") @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        CrmProduct crmProduct = new CrmProduct();
        crmProduct.setCategoryId(categoryId);
        crmProduct.setSerial(serial);
        crmProduct.setName(name);
        crmProduct.setCostPrice(new BigDecimal(costPrice));
        crmProduct.setSellingPrice(new BigDecimal(sellingPrice));
        crmProduct.setUnit(unit);
        crmProduct.setCreateTime(SQLDatetime.time());
        crmProduct.setStatus(status);
        crmProduct.setRemark(remark);

        boolean flag = crmProductService.save(crmProduct);
        if (flag) {
            return ResultFormat.success("新增成功", crmProduct.getId());
        }
        return ResultFormat.error("新增失败");
    }

    @ApiOperation(value = "新增分类")
    @RequestMapping(value = "addCategory", method = RequestMethod.POST)
    public Object addCategory(
            @ApiParam(value = "上级ID") @RequestParam(value = "parentId", defaultValue = "0") String parentId,
            @ApiParam(value = "产品名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "排序") @RequestParam(value = "sort", defaultValue = "99") Integer sort
    ) {
        CrmProductCategory crmProductCategory = new CrmProductCategory();
        crmProductCategory.setParentId(parentId);
        crmProductCategory.setName(name);
        crmProductCategory.setSort(sort);
        boolean result = crmProductCategoryService.save(crmProductCategory);
        if (result) {
            return ResultFormat.success("新增成功", "");
        }
        return ResultFormat.error("新增失败");
    }

    // --- 编辑 ----------------------------------------------------------

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id,
            @ApiParam(value = "所属分类") @RequestParam(value = "categoryId") String categoryId,
            @ApiParam(value = "产品编号") @RequestParam(value = "serial") String serial,
            @ApiParam(value = "产品名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "成本价") @RequestParam(value = "costPrice") Double costPrice,
            @ApiParam(value = "销售价") @RequestParam(value = "sellingPrice") Double sellingPrice,
            @ApiParam(value = "单位") @RequestParam(value = "unit") String unit,
            @ApiParam(value = "状态") @RequestParam(value = "status") Integer status,
            @ApiParam(value = "备注") @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        CrmProduct crmProduct = new CrmProduct();
        crmProduct.setCategoryId(categoryId);
        crmProduct.setSerial(serial);
        crmProduct.setName(name);
        crmProduct.setCostPrice(new BigDecimal(costPrice));
        crmProduct.setSellingPrice(new BigDecimal(sellingPrice));
        crmProduct.setUnit(unit);
        crmProduct.setStatus(status);
        crmProduct.setRemark(remark);

        boolean flag = crmProductService.update(crmProduct, new QueryWrapper<CrmProduct>().eq(CrmProduct.ID, id));
        if (flag) {
            return ResultFormat.success("编辑成功", "");
        }
        return ResultFormat.error("编辑失败");
    }

    @ApiOperation(value = "编辑分类")
    @RequestMapping(value = "editCategory", method = RequestMethod.PUT)
    public Object editCategory(
            @ApiParam(value = "主键") @RequestParam(value = "id") String id,
            @ApiParam(value = "上级ID") @RequestParam(value = "parentId", defaultValue = "0") String parentId,
            @ApiParam(value = "产品名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "排序") @RequestParam(value = "sort") Integer sort
    ) {
        CrmProductCategory crmProductCategory = new CrmProductCategory();
        crmProductCategory.setParentId(parentId);
        crmProductCategory.setName(name);
        crmProductCategory.setSort(sort);
        boolean result = crmProductCategoryService.update(crmProductCategory, new QueryWrapper<CrmProductCategory>().eq(CrmProductCategory.ID, id));
        if (result) {
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
        boolean result = crmProductService.removeById(id);
        if (result) {
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除失败");
    }

    @ApiOperation(value = "删除分类")
    @RequestMapping(value = "deleteCategory", method = RequestMethod.DELETE)
    public Object deleteCategory(
            @ApiParam(value = "分类ID") @RequestParam(value = "id") String id
    ) {
        int childCount = crmProductCategoryService.count(new QueryWrapper<CrmProductCategory>().eq(CrmProductCategory.PARENT_ID, id));
        if (childCount > 0) {
            return ResultFormat.error("请先删除下级");
        }
        boolean result = crmProductCategoryService.removeById(id);
        if (result) {
            crmProductService.remove(new QueryWrapper<CrmProduct>().eq(CrmProduct.CATEGORY_ID, id));
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除失败");
    }

}