package com.yj.yuaiagent.demo.invoke;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 阿里云灵积 AI Http调用示例
 */
public class HttpAiInvoke {

    public static void main(String[] args) {
        // 配置参数
        String workspaceId = "ws-i5fgqd12r9j55xwe";
        String apiKey = ApiKey.API_KEY; // 或直接填写 "sk-xxx"
        String url = String.format(
                "https://%s.cn-beijing.maas.aliyuncs.com/api/v1/services/aigc/multimodal-generation/generation",
                workspaceId
        );

        // 构建 system message
        JSONObject systemMessage = new JSONObject()
                .set("role", "system")
                .set("content", new JSONArray()
                        .put(new JSONObject().set("text", "You are a helpful assistant.")));

        // 构建 user message
        JSONObject userMessage = new JSONObject()
                .set("role", "user")
                .set("content", new JSONArray()
                        .put(new JSONObject().set("text", "你是谁？")));

        // 构建 messages
        JSONArray messages = new JSONArray()
                .put(systemMessage)
                .put(userMessage);

        // 构建参数
        JSONObject parameters = new JSONObject()
                .set("result_format", "message");

        // 构建完整请求体
        JSONObject body = new JSONObject()
                .set("model", "qwen3.8-max")
                .set("input", new JSONObject().set("messages", messages))
                .set("parameters", parameters);

        // 发起 POST 请求
        try (HttpResponse response = HttpRequest.post(url)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(body.toString())
                .timeout(60000) // 可选：设置超时
                .execute()) {

            System.out.println("状态码: " + response.getStatus());
            System.out.println("响应体: " + response.body());

            // 如果需要解析响应
            if (response.isOk()) {
                JSONObject result = JSONUtil.parseObj(response.body());
                System.out.println("格式化结果: " + result.toStringPretty());
            }
        }
    }
}