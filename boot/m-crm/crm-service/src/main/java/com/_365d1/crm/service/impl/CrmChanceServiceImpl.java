package com._365d1.crm.service.impl;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: Nunumao代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-15 10:44
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.crm.dao.CrmChanceDao;
import com._365d1.crm.model.CrmChance;
import com._365d1.crm.model.vo.CrmChanceDetailVo;
import com._365d1.crm.model.vo.CrmChanceListVo;
import com._365d1.crm.service.CrmChanceService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmChanceServiceImpl extends ServiceImpl<CrmChanceDao, CrmChance> implements CrmChanceService {

    @Autowired
    private CrmChanceDao crmChanceDao;

    @Override
    public IPage<CrmChanceListVo> queryPage(String search, Integer page, Integer size) {
        return crmChanceDao.queryPage(search, new Page<>(page, size));
    }

    @Override
    public CrmChanceDetailVo queryDetail(String id) {
        return crmChanceDao.queryDetail(id);
    }

}