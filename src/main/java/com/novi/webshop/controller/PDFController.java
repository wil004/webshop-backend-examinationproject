package com.novi.webshop.controller;

import com.novi.webshop.services.PDFGeneratorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class PDFController {
    private final PDFGeneratorService pdfGeneratorService;

    public PDFController(PDFGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }


    @GetMapping("/pdf/generate/{orderId}")
    public void generatePDF(HttpServletResponse response, @PathVariable Long orderId) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + "id=" + orderId;
        response.setHeader(headerKey, headerValue);
        this.pdfGeneratorService.orderInvoice(response, orderId);
    }
}