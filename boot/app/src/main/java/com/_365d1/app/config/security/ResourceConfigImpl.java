package com._365d1.app.config.security;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/9/8
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import io.github.nunumao.security.config.ResourceConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceConfigImpl implements ResourceConfig {

    @Override
    public void configure(List<String> matchers, List<String> ignores) throws Exception {
        matchers.add("/actuator/**");
    }

}
