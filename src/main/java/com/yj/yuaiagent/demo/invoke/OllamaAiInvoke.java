package com.yj.yuaiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Spring ai 接入本地大模型Ollama
 */
//@Component
public class OllamaAiInvoke implements CommandLineRunner {

    @Resource(name = "ollamaChatModel")
    private ChatModel ollamaChatModel;

    @Override
    public void run(String... args) throws Exception {
        AssistantMessage assistantMessage = ollamaChatModel.call(new Prompt("你好，我是yujiang,我正在学习spring ai 接入本地大模型"))
                .getResult()
                .getOutput();

        System.out.println(assistantMessage.getText());
    }
}
