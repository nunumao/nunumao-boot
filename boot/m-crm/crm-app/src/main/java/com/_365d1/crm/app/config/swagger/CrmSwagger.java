package com._365d1.crm.app.config.swagger;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/10/15
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import io.github.nunumao.core.config.swagger.DocketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashMap;

@Configuration
public class CrmSwagger {

    @Autowired
    private DocketUtils docketUtils;

    @Bean
    public Docket crm() {
        return docketUtils.buildDocket(
                "CRM模块",
                "com._365d1.crm.app.controller",
                docketUtils.buildApiInfo("CRM模块", "v 0.0.1", "更新时间 2020年3月26日"),
                new HashMap<String, String>() {{
                    put("all", "默认");
                }}
        );
    }

}
