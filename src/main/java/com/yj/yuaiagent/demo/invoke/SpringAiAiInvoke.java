package com.yj.yuaiagent.demo.invoke;


import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * 阿里云灵积 AI Sping Ai alibaba调用示例
 */
//@Component
public class SpringAiAiInvoke implements CommandLineRunner {

    @Resource(name = "dashscopeChatModel")
    private ChatModel dashscopeChatModel;

    @Override
    public void run(String... args) throws Exception {
        AssistantMessage output = dashscopeChatModel.call(
                new Prompt("我正在学习spring ai alibaba调用示例")
        ).getResult().getOutput();
        System.out.println("output:"+output.getText());
    }
}
