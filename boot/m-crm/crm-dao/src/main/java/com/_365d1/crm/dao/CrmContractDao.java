package com._365d1.crm.dao;
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

import com._365d1.crm.model.CrmContract;
import com._365d1.crm.model.vo.CrmContractDetailVo;
import com._365d1.crm.model.vo.CrmContractVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

public interface CrmContractDao extends BaseMapper<CrmContract> {

    IPage<CrmContractVo> queryPage(
            @Param(value = "search") String search,
            IPage<CrmContractVo> page);

    CrmContractDetailVo queryDetail(@Param(value = "id") String id);

}