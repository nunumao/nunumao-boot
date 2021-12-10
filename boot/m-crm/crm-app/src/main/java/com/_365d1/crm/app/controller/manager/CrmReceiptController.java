package com._365d1.crm.app.controller.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: Nunumao 代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-29 03:41
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import cn.hutool.core.date.DateUtil;
import com._365d1.crm.app.controller.Manager;
import com._365d1.crm.model.CrmReceipt;
import com._365d1.crm.service.CrmReceiptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.nunumao.common.utils.ResultFormat;
import io.github.nunumao.common.utils.SQLDatetime;
import io.github.nunumao.common.utils.login.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;


@Api(tags = "收款管理")
@RestController(value = Manager.CRTL_PREFIX + "crmReceiptController")
@RequestMapping(value = "receipt")
public class CrmReceiptController extends Manager<CrmReceiptService, CrmReceipt> {

    @Autowired
    private CrmReceiptService crmReceiptService;

    // --- 查询 ----------------------------------------------------------

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") int page,
            @ApiParam(value = "页大小") @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        IPage<CrmReceipt> list = crmReceiptService.page(new Page<>(page, size));
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
        CrmReceipt crmReceipt = crmReceiptService.getById(id);
        if (!ObjectUtils.isEmpty(crmReceipt)) {
            return ResultFormat.success("查询成功", crmReceipt);
        }
        return ResultFormat.error("没有数据");
    }

    // --- 新增 ----------------------------------------------------------

    @ApiOperation(value = "新增")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "用户ID") @RequestParam(value = "userId") String userId,
            @ApiParam(value = "合同ID") @RequestParam(value = "contractId") String contractId,
            @ApiParam(value = "收款方式[0 银行转账 | 1 微信支付 | 2 支付宝 | 3 网银转账 | 4 现金 | 5 其他]") @RequestParam(value = "payType") Integer payType,
            @ApiParam(value = "本次到账") @RequestParam(value = "price") Double price,
            @ApiParam(value = "收款时间") @RequestParam(value = "priceDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date priceDate,
            @ApiParam(value = "票据类型[0 发票 | 1 收据 | 2 其他]") @RequestParam(value = "billType") Integer billType,
            @ApiParam(value = "票据编号") @RequestParam(value = "billSerial", defaultValue = "") String billSerial,
            @ApiParam(value = "备注") @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        CrmReceipt crmReceipt = new CrmReceipt();
        crmReceipt.setUserId(userId);
        crmReceipt.setCreatorUserId(LoginUser.current().id);
        crmReceipt.setContractId(contractId);
        crmReceipt.setPayType(payType);
        crmReceipt.setPrice(new BigDecimal(price));
        crmReceipt.setPriceDate(SQLDatetime.date(DateUtil.date(priceDate)));
        crmReceipt.setBillType(billType);
        crmReceipt.setBillSerial(billSerial);
        crmReceipt.setRemark(remark);
        crmReceipt.setCreateTime(SQLDatetime.time(DateUtil.date()));

        boolean flag = crmReceiptService.save(crmReceipt);
        if (flag) {
            return ResultFormat.success("新增成功", crmReceipt.getId());
        }
        return ResultFormat.error("新增失败");
    }

    // --- 编辑 ----------------------------------------------------------

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id,
            @ApiParam(value = "用户ID") @RequestParam(value = "userId") String userId,
            @ApiParam(value = "合同ID") @RequestParam(value = "contractId") String contractId,
            @ApiParam(value = "收款方式[0 银行转账 | 1 微信支付 | 2 支付宝 | 3 网银转账 | 4 现金 | 5 其他]") @RequestParam(value = "payType") Integer payType,
            @ApiParam(value = "本次到账") @RequestParam(value = "price") Double price,
            @ApiParam(value = "收款时间") @RequestParam(value = "priceDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date priceDate,
            @ApiParam(value = "票据类型[0 发票 | 1 收据 | 2 其他]") @RequestParam(value = "billType") Integer billType,
            @ApiParam(value = "票据编号") @RequestParam(value = "billSerial", defaultValue = "") String billSerial,
            @ApiParam(value = "备注") @RequestParam(value = "remark", defaultValue = "") String remark
    ) {
        CrmReceipt crmReceipt = new CrmReceipt();
        crmReceipt.setUserId(userId);
        crmReceipt.setCreatorUserId(LoginUser.current().id);
        crmReceipt.setContractId(contractId);
        crmReceipt.setPayType(payType);
        crmReceipt.setPrice(new BigDecimal(price));
        crmReceipt.setPriceDate(SQLDatetime.date(DateUtil.date(priceDate)));
        crmReceipt.setBillType(billType);
        crmReceipt.setBillSerial(billSerial);
        crmReceipt.setRemark(remark);
        crmReceipt.setCreateTime(SQLDatetime.time(DateUtil.date()));

        boolean flag = crmReceiptService.update(crmReceipt, new QueryWrapper<CrmReceipt>().eq(CrmReceipt.ID, id));
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
        boolean result = crmReceiptService.removeById(id);
        if (result) {
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除失败");
    }

}