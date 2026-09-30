package com.yj.yuaiagent.agent;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class YuManusTest {

    @Resource
    private YuManus yuManus;

    @Test
    public void test() {
        String userPrompt = """
                我想带我女朋友去重庆旅游
                包括景点、美食、住宿、交通等，并结合网络图片给出详细的行程安排。
                并以 PDF 格式输出
                """;
        String answer = yuManus.run(userPrompt);
        Assertions.assertNotNull(answer);
    }
}