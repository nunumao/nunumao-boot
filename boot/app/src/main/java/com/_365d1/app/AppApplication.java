package com._365d1.app;
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

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@MapperScan(value = {"io.github.nunumao.*.dao", "com._365d1.*.dao"})
@SpringBootApplication(scanBasePackages = {"io.github.nunumao", "com._365d1"})
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AppApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }

}
