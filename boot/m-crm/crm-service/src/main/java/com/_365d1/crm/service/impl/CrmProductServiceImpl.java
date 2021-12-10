package com._365d1.crm.service.impl;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: Nunumao代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-11-23 09:21
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.crm.dao.CrmProductDao;
import com._365d1.crm.model.CrmProduct;
import com._365d1.crm.model.vo.CrmProductVo;
import com._365d1.crm.service.CrmProductService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmProductServiceImpl extends ServiceImpl<CrmProductDao, CrmProduct> implements CrmProductService {

    @Autowired
    private CrmProductDao crmProductDao;

    @Override
    public IPage<CrmProductVo> queryPage(String search, String category, Integer page, Integer size) {
        return crmProductDao.queryPage(search, category, new Page<>(page, size));
    }

    @Override
    public CrmProductVo queryDetail(String id) {
        return crmProductDao.queryDetail(id);
    }

}