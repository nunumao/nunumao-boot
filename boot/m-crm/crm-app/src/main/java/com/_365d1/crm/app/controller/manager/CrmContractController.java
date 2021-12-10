package com._365d1.crm.app.controller.manager;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: Nunumao 代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-26 07:56
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
import com._365d1.crm.model.vo.CrmContractDetailVo;
import com._365d1.crm.model.vo.CrmContractVo;
import com._365d1.crm.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.nunumao.common.utils.GsonUtils;
import io.github.nunumao.common.utils.ResultFormat;
import io.github.nunumao.common.utils.SQLDatetime;
import io.github.nunumao.common.utils.enums.SuggestEnum;
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Api(tags = "合同管理")
@RestController(value = Manager.CRTL_PREFIX + "crmContractController")
@RequestMapping(value = "contract")
public class CrmContractController extends Manager<CrmContractService, CrmContract> {

    @Autowired
    private CrmContractService crmContractService;

    @Autowired
    private CrmContractOrderService crmContractOrderService;

    @Autowired
    private CrmCustomerService crmCustomerService;

    @Autowired
    private CrmChanceService crmChanceService;

    @Autowired
    private CrmReceiptService crmReceiptService;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    // --- 查询 ----------------------------------------------------------

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(
            @ApiParam(value = "条件") @RequestParam(value = "search", defaultValue = "") String search,
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam(value = "页大小") @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        IPage<CrmContractVo> list = crmContractService.queryPage(search, page, size);
        if (list.getRecords().size() > 0) {
            return ResultFormat.success("查询成功", list);
        }
        return ResultFormat.error("查询失败");
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public Object detail(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        CrmContractDetailVo crmContractDetailVo = crmContractService.queryDetail(id);
        if (!ObjectUtils.isEmpty(crmContractDetailVo)) {
            crmContractDetailVo.setReceiptList(crmReceiptService.list(new QueryWrapper<CrmReceipt>()
                    .eq(CrmReceipt.CONTRACT_ID, crmContractDetailVo.getId())));
            return ResultFormat.success("查询成功", crmContractDetailVo);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id
    ) {
        CrmContract crmContract = crmContractService.getById(id);
        if (!ObjectUtils.isEmpty(crmContract)) {
            crmContract.setOrderList(crmContractOrderService.list(new QueryWrapper<CrmContractOrder>()
                    .eq(CrmContractOrder.CONTRACT_ID, crmContract.getId())));
            return ResultFormat.success("查询成功", crmContract);
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "客户")
    @RequestMapping(value = "customer", method = RequestMethod.GET)
    public Object customer(
            @ApiParam(value = "操作") @RequestParam(value = "action", defaultValue = "list") String action,
            @ApiParam(value = "ID") @RequestParam(value = "id", defaultValue = "") String id,
            @ApiParam(value = "条件") @RequestParam(value = "search", defaultValue = "") String search,
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        if (action.equals(SuggestEnum.LIST.getValue())) {
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
        } else if (action.equals(SuggestEnum.DETAIL.getValue())) {
            CrmCustomer crmCustomer = crmCustomerService.getById(id);
            if (!ObjectUtils.isEmpty(crmCustomer)) {
                return ResultFormat.success("查询成功", crmCustomer);
            }
        }
        return ResultFormat.error("没有数据");
    }

    @ApiOperation(value = "商机")
    @RequestMapping(value = "chance", method = RequestMethod.GET)
    public Object chance(
            @ApiParam(value = "操作") @RequestParam(value = "action", defaultValue = "list") String action,
            @ApiParam(value = "ID") @RequestParam(value = "id", defaultValue = "") String id,
            @ApiParam(value = "条件") @RequestParam(value = "search", defaultValue = "") String search,
            @ApiParam(value = "页码") @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        if (action.equals(SuggestEnum.LIST.getValue())) {
            QueryWrapper<CrmChance> query = new QueryWrapper<>();
            if (StringUtils.hasLength(search)) {
                query.eq(CrmChance.SERIAL, search)
                        .or()
                        .eq(CrmChance.NAME, search);
            }
            IPage<CrmChance> list = crmChanceService.page(new Page<>(page, this.pageSize), query);
            if (list.getRecords().size() > 0) {
                return ResultFormat.success("查询成功", list);
            }
        } else if (action.equals(SuggestEnum.DETAIL.getValue())) {
            CrmChance crmChance = crmChanceService.getById(id);
            if (!ObjectUtils.isEmpty(crmChance)) {
                return ResultFormat.success("查询成功", crmChance);
            }
        }
        return ResultFormat.error("没有数据");
    }

    // --- 新增 ----------------------------------------------------------

    @ApiOperation(value = "新增")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(
            @ApiParam(value = "所属用户") @RequestParam(value = "userId") String userId,
            @ApiParam(value = "合同编号") @RequestParam(value = "serial") String serial,
            @ApiParam(value = "合同名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "合同类型") @RequestParam(value = "type") Integer type,
            @ApiParam(value = "客户ID") @RequestParam(value = "customerId") String customerId,
            @ApiParam(value = "商机ID") @RequestParam(value = "chanceId") String chanceId,
            @ApiParam(value = "总金额") @RequestParam(value = "totalPrice") Float totalPrice,
            @ApiParam(value = "商务费用") @RequestParam(value = "rebatePrice") Float rebatePrice,
            @ApiParam(value = "签订期限") @RequestParam(value = "signDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date signDate,
            @ApiParam(value = "合同期限[留空为永久]") @RequestParam(value = "expireDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date expireDate,
            @ApiParam(value = "备注") @RequestParam(value = "remark") String remark,
            @ApiParam(value = "状态[0 未执行 | 1 执行中 | 2 结束 | 3 终止]") @RequestParam(value = "status") Integer status,
            @ApiParam(value = "订单详情") @RequestParam(value = "orderList", defaultValue = "") String orderList
    ) {
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            CrmContract crmContract = new CrmContract();
            crmContract.setUserId(userId);
            crmContract.setCreatorUserId(LoginUser.current().id);
            crmContract.setType(type);
            crmContract.setSerial(serial);
            crmContract.setName(name);
            crmContract.setCustomerId(customerId);
            crmContract.setChanceId(chanceId);
            crmContract.setTotalPrice(new BigDecimal(totalPrice));
            crmContract.setRebatePrice(new BigDecimal(rebatePrice));
            crmContract.setSignDate(SQLDatetime.date(DateUtil.date(signDate)));
            if (!ObjectUtils.isEmpty(expireDate)) {
                crmContract.setExpireDate(SQLDatetime.date(DateUtil.date(expireDate)));
            }
            crmContract.setCreateTime(SQLDatetime.time(DateUtil.date()));
            crmContract.setRemark(remark);
            crmContract.setStatus(status);
            crmContractService.save(crmContract);

            List<CrmContractOrder> orders = GsonUtils.toList(orderList, CrmContractOrder.class);
            if (orders.size() > 0) {
                orders.forEach(item -> item.setContractId(crmContract.getId()));
                crmContractOrderService.saveBatch(orders);
            }

            transactionManager.commit(transactionStatus);
            return ResultFormat.success("新增成功", "");
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
            return ResultFormat.error("新增失败");
        }
    }

    // --- 编辑 ----------------------------------------------------------

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "edit", method = RequestMethod.PUT)
    public Object edit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id,
            @ApiParam(value = "所属用户") @RequestParam(value = "userId") String userId,
            @ApiParam(value = "合同编号") @RequestParam(value = "serial") String serial,
            @ApiParam(value = "合同名称") @RequestParam(value = "name") String name,
            @ApiParam(value = "合同类型") @RequestParam(value = "type") Integer type,
            @ApiParam(value = "客户ID") @RequestParam(value = "customerId") String customerId,
            @ApiParam(value = "商机ID") @RequestParam(value = "chanceId") String chanceId,
            @ApiParam(value = "总金额") @RequestParam(value = "totalPrice") Float totalPrice,
            @ApiParam(value = "商务费用") @RequestParam(value = "rebatePrice") Float rebatePrice,
            @ApiParam(value = "签订期限") @RequestParam(value = "signDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date signDate,
            @ApiParam(value = "合同期限[留空为永久]") @RequestParam(value = "expireDate", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date expireDate,
            @ApiParam(value = "备注") @RequestParam(value = "remark") String remark,
            @ApiParam(value = "状态[0 未执行 | 1 执行中 | 2 结束 | 3 终止]") @RequestParam(value = "status") Integer status,
            @ApiParam(value = "订单详情") @RequestParam(value = "orderList", defaultValue = "") String orderList
    ) {
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            CrmContract crmContract = new CrmContract();
            crmContract.setUserId(userId);
            crmContract.setEditorUserId(LoginUser.current().id);
            crmContract.setType(type);
            crmContract.setSerial(serial);
            crmContract.setName(name);
            crmContract.setCustomerId(customerId);
            crmContract.setChanceId(chanceId);
            crmContract.setTotalPrice(new BigDecimal(totalPrice));
            crmContract.setRebatePrice(new BigDecimal(rebatePrice));
            crmContract.setSignDate(SQLDatetime.date(DateUtil.date(signDate)));
            if (!ObjectUtils.isEmpty(expireDate)) {
                crmContract.setExpireDate(SQLDatetime.date(DateUtil.date(expireDate)));
            }
            crmContract.setUpdateTime(SQLDatetime.time(DateUtil.date()));
            crmContract.setRemark(remark);
            crmContract.setStatus(status);
            crmContractService.update(crmContract, new QueryWrapper<CrmContract>().eq(CrmContract.ID, id));

            List<CrmContractOrder> orders = GsonUtils.toList(orderList, CrmContractOrder.class);
            if (orders.size() > 0) {
                crmContractOrderService.remove(new QueryWrapper<CrmContractOrder>().eq(CrmContractOrder.CONTRACT_ID, id));
                orders.forEach(item -> item.setContractId(id));
                crmContractOrderService.saveBatch(orders);
            }

            transactionManager.commit(transactionStatus);
            return ResultFormat.success("编辑成功", "");
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(transactionStatus);
            return ResultFormat.error("编辑失败");
        }
    }

    // --- 删除 ----------------------------------------------------------

    @ApiOperation(value = "删除")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Object delete(
            @ApiParam(value = "主键") @RequestParam(value = "id") String id
    ) {
        boolean result = crmContractService.removeById(id);
        if (result) {
            crmContractOrderService.remove(new QueryWrapper<CrmContractOrder>().eq(CrmContractOrder.CONTRACT_ID, id));
            crmReceiptService.remove(new QueryWrapper<CrmReceipt>().eq(CrmReceipt.CONTRACT_ID, id));
            return ResultFormat.success("删除成功", "");
        }
        return ResultFormat.error("删除失败");
    }

}