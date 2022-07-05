package com.novi.webshop.controller;

import com.novi.webshop.services.PDFGeneratorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("pdf")
public class PDFController {
    private final PDFGeneratorService pdfGeneratorService;

    public PDFController(PDFGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }


    @GetMapping("/generate-order/id={orderId}")
    public void generateOrderPdf(HttpServletResponse response, @PathVariable Long orderId) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + "id=" + orderId;
        response.setHeader(headerKey, headerValue);
        this.pdfGeneratorService.orderInvoice(response, orderId);
    }


    @GetMapping("/generate-return/id={returnsId}")
    public void generateReturnPdf(HttpServletResponse response, @PathVariable Long returnsId) throws IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + "id=" + returnsId;
        response.setHeader(headerKey, headerValue);
        this.pdfGeneratorService.returnsInvoice(response, returnsId);
    }
}