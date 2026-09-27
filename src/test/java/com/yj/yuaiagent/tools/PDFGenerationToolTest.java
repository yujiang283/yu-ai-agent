package com.yj.yuaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PDFGenerationToolTest {

    @Test
    void generatePDF() {
        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();
        String fileName = "test.pdf";
        String content = "This is a test PDF file.";
        String result = pdfGenerationTool.generatePDF(fileName,content );
        Assertions.assertNotNull(result);
    }
}