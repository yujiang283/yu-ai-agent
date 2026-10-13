package com.yj.yuaiagent.tools;

import org.springframework.ai.tool.annotation.Tool;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 获取当前日期、时间（使用大模型的时间可能不准确）
 * @author Lenovo
 */
public class TimeTool {
    @Tool(description = "获取当前日期、时间和时区")
    public String now() {
        return LocalDateTime.now()
                .atZone(ZoneId.of("Asia/Shanghai"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss EEEE"));
    }
}