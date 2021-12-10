package com._365d1.crm.service;
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

import com._365d1.crm.model.CrmChance;
import com._365d1.crm.model.vo.CrmChanceDetailVo;
import com._365d1.crm.model.vo.CrmChanceListVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CrmChanceService extends IService<CrmChance> {

    IPage<CrmChanceListVo> queryPage(String search, Integer page, Integer size);

    CrmChanceDetailVo queryDetail(String id);

}