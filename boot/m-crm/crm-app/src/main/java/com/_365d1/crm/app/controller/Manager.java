package com._365d1.crm.app.controller;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/9/7
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import io.github.nunumao.core.controller.ManagerBase;

public class Manager<S,E> extends ManagerBase<S,E> {

    public static final String PREFIX = "crm";

    public Manager() {
        super(PREFIX);
    }

}
