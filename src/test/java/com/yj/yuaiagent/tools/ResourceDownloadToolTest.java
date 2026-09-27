package com.yj.yuaiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceDownloadToolTest {

    @Test
    void downloadResource() {
        ResourceDownloadTool tool = new ResourceDownloadTool();
        String result = tool.downloadResource("https://www.baidu.com", "baidu.html");
        assertNotNull(result);
    }
}