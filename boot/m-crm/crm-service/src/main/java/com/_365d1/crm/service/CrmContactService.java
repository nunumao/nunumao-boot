package com._365d1.crm.service;
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

import com._365d1.crm.model.CrmContact;
import com._365d1.crm.model.vo.CrmContactDetailVo;
import com._365d1.crm.model.vo.CrmContactListVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CrmContactService extends IService<CrmContact> {

    IPage<CrmContactListVo> queryPage(String search, String customer, Integer page, Integer size);

    CrmContactDetailVo queryDetail(String id);

}