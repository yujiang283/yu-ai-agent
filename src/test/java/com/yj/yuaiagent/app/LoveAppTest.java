package com.yj.yuaiagent.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoveAppTest {

    @Autowired
    private LoveApp loveApp;

    @Test
    void doChat() {
        String chatId = UUID.randomUUID().toString();
        //第一轮
        String message = "你好，我是小明";
        String answer = loveApp.doChat(message, chatId);
        //第二轮
        message = "我喜欢隔壁班的李华";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        //第三轮
        message = "你好，我的名字叫什么";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好，我是程序员小明，我想让另一半（李华）更爱我，但我不知道该怎么做";
        LoveApp.LoveReport loveReport = loveApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(loveReport);
    }

    @Test
    void doChatWithVectoreStore() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好，我是程序员小明，我媳妇（李华）与我的妈妈矛盾、家庭关系紧张，不融洽，该怎么处理？";
        String answer = loveApp.doChatWithVectoreStore(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithTool() {
        // 测试联网搜索问题的答案
        testMessage("周末想带女朋友去重庆约会，推荐几个适合情侣的小众打卡地？");

        // 测试网页抓取：恋爱案例分析
        testMessage("最近和对象吵架了，看看github（https://github.com/yujiang283/）的其他情侣是怎么解决矛盾的？");

        // 测试资源下载：图片下载
        testMessage("直接下载一张适合做手机壁纸的星空情侣图片为文件");

        // 测试终端操作：执行代码
        testMessage("执行 Python3 脚本来生成数据分析报告");

        // 测试文件操作：保存用户档案
        testMessage("保存我的恋爱档案为文件");

        // 测试 PDF 生成
        testMessage("生成一份‘七夕约会计划’PDF，包含餐厅预订、活动流程和礼物清单");
    }

    private void testMessage(String message) {
        String chatId = UUID.randomUUID().toString();
        String answer = loveApp.doChatWithTools(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithMcp() {
        String chatId = UUID.randomUUID().toString();
        //测试图片搜索MCP
        String message = "我想找一张适合做手机壁纸的星空情侣图片";
        String result = loveApp.doChatWithMcp(message, chatId);
        Assertions.assertNotNull(result);
    }
}