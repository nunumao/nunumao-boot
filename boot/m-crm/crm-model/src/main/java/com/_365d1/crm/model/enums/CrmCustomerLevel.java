package com._365d1.crm.model.enums;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/10/20
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import io.github.nunumao.common.utils.enums.Dictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CrmCustomerLevel implements Dictionary {

    UNASSORTED("未分级", 0),
    SMALL("小型客户", 1),
    MIDDLE("中型客户", 2),
    LARGE("大型客户", 3),
    VIP("VIP客户", 4);

    private String key;
    private Object value;

}
