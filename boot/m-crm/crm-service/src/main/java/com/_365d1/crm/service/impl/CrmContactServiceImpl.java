package com._365d1.crm.service.impl;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: Nunumao代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-10-21 02:56
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.crm.dao.CrmContactDao;
import com._365d1.crm.model.CrmContact;
import com._365d1.crm.model.vo.CrmContactDetailVo;
import com._365d1.crm.model.vo.CrmContactListVo;
import com._365d1.crm.service.CrmContactService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmContactServiceImpl extends ServiceImpl<CrmContactDao, CrmContact> implements CrmContactService {

    @Autowired
    private CrmContactDao crmContactDao;

    @Override
    public IPage<CrmContactListVo> queryPage(String search, String customer, Integer page, Integer size) {
        return crmContactDao.queryPage(search, customer, new Page<>(page, size));
    }

    @Override
    public CrmContactDetailVo queryDetail(String id) {
        return crmContactDao.queryDetail(id);
    }

}