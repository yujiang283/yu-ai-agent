package com.yj.yuaiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminalOperationToolTest {

    @Test
    void executeTerminalCommand() {
        TerminalOperationTool tool = new TerminalOperationTool();
        String commond = "ipconfig";
        String result = tool.executeTerminalCommand(commond);
        assertNotNull(result);
    }
}