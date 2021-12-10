package com._365d1.app.controller;
// +----------------------------------------------------------------------
// | 官方网站: www.365d1.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/12/2
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import io.github.nunumao.common.utils.websocket.topic.WebSocketSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "appTestController")
@RequestMapping(value = "test")
public class TestController {

    @Autowired
    private WebSocketSender webSocketSender;

    @RequestMapping(value = "all")
    public void all() {
        webSocketSender.sendBroadcast("测试消息", "您有一条新的消息", "消息内容好长好长的...");
    }

    @RequestMapping(value = "one")
    public void one(
            @RequestParam(value = "id") String id
    ) {
//        webSocketSender.sendUser(id, "随机数->" + Math.random() + "");
    }

}
