package com.yj.yuaiagent.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CalendarEventStatus {

    ACTIVE("正常日程"),
    DONE("已完成"),
    CANCELLED("已取消");

    private final String desc;

    /**
     * 根据名称解析枚举，非法值返回 null
     */
    public static CalendarEventStatus fromName(String name) {
        if (name == null) return null;
        for (CalendarEventStatus s : values()) {
            if (s.name().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    /**
     * 判断是否合法状态值
     */
    public static boolean isValid(String name) {
        return fromName(name) != null;
    }

    /**
     * 是否终态（不可再变更）
     */
    public boolean isFinal() {
        return this == DONE || this == CANCELLED;
    }
}