package com._365d1.crm.service.impl;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: Nunumao代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-26 07:56
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.crm.dao.CrmContractDao;
import com._365d1.crm.model.CrmContract;
import com._365d1.crm.model.vo.CrmContractDetailVo;
import com._365d1.crm.model.vo.CrmContractVo;
import com._365d1.crm.service.CrmContractService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmContractServiceImpl extends ServiceImpl<CrmContractDao, CrmContract> implements CrmContractService {

    @Autowired
    private CrmContractDao crmContractDao;

    @Override
    public IPage<CrmContractVo> queryPage(String search, Integer page, Integer size) {
        return crmContractDao.queryPage(search, new Page<>(page, size));
    }

    @Override
    public CrmContractDetailVo queryDetail(String id) {
        return crmContractDao.queryDetail(id);
    }

}