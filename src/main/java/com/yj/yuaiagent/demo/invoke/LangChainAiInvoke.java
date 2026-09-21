package com.yj.yuaiagent.demo.invoke;


import dev.langchain4j.community.model.dashscope.QwenChatModel;

/**
 * 阿里云灵积 AI LangChain4J调用示例
 */
public class LangChainAiInvoke{
    public static void main(String[] args) {
        QwenChatModel qwenChatModel = QwenChatModel.builder()
                .apiKey(ApiKey.API_KEY)
                .modelName("qwen3.7-plus")
                .build();

        String answer = qwenChatModel.chat("我正在学习使用LangChain4J接入阿里云百炼平台，你能给我一些帮助吗？");
        System.out.println(answer);
    }
}
