package com.novi.webshop.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.novi.webshop.model.Admin;
import com.novi.webshop.model.Customer;
import com.novi.webshop.model.Orders;
import com.novi.webshop.model.Returns;
import com.novi.webshop.repository.AdminRepository;
import com.novi.webshop.repository.OrderRepository;
import com.novi.webshop.repository.ReturnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PDFGeneratorService {

    @Autowired
    private final OrderRepository orderRepository;
    private final ReturnsRepository returnsRepository;
    private final AdminRepository adminRepository;

    public PDFGeneratorService(OrderRepository orderRepository, ReturnsRepository returnsRepository, AdminRepository adminRepository) {
        this.orderRepository = orderRepository;
        this.returnsRepository = returnsRepository;
        this.adminRepository = adminRepository;
    }

    public void orderInvoice(HttpServletResponse response, Long orderId) throws IOException {
        if(orderRepository.findById(orderId).isPresent()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            Orders order = orderRepository.findById(orderId).orElseThrow();
            Admin admin = adminRepository.findAll().get(0);
            document.open();
            Font fontTitle = FontFactory.getFont(FontFactory.TIMES_BOLD);
            fontTitle.setSize(24);

            Paragraph invoiceTitle = new Paragraph("Bestelling van " + order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName(), fontTitle);
            invoiceTitle.setAlignment(Paragraph.ALIGN_CENTER);

            Font fontAddress = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            fontAddress.setSize(14);

            Font fontProductTitle = FontFactory.getFont(FontFactory.TIMES_BOLD);
            fontProductTitle.setSize(16);

            Font fontProducts = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            fontProducts.setSize(12);

            if(order.getCustomer().getAdditionalToHouseNumber() == null) {
                order.getCustomer().setAdditionalToHouseNumber("");
            }

            Paragraph paymentInformationTitle = new Paragraph("PAYMENT INFORMATION"  + "\n" + "Order-number: " + order.getId(), fontProductTitle);

            Paragraph paymentInformation = new Paragraph("Send your payment to the following bank-number " + "\n" +
                    admin.getBankAccount() + "\n" +
                    "Add your order number in the banking details/messages so we know the order has been paid." + "\n"
            + "The total price of your order: €" + order.getTotalPrice() + "\n" + "We will process your order within 7 working days!" +
                    "\n" + "\n");

            Paragraph productTitle = new Paragraph("ORDERED PRODUCTS", fontProductTitle);
            productTitle.setAlignment(Paragraph.ALIGN_CENTER);

            String orderList = "";
            for (int i = 0; i < order.getQuantityAndProductList().size(); i++) {
                orderList = orderList + "product: " + order.getQuantityAndProductList().get(i).getProduct().getProductName() + "         |         " + "Price per piece: " +
                        order.getQuantityAndProductList().get(i).getProduct().getPrice() + " | " +
                        "Amount: " + order.getQuantityAndProductList().get(i).getAmountOfProducts() + " | " +
                        " Total price: " + order.getQuantityAndProductList().get(i).getProduct().getPrice() * order.getQuantityAndProductList().get(i).getAmountOfProducts() + "\n";
            }
            Paragraph products = new Paragraph(orderList, fontProducts);
            products.setAlignment(Paragraph.ALIGN_CENTER);

            Paragraph addressInformation = new Paragraph("\n" + "\n" + "AddressDetails: " + "\n" +
                    " Straatnaam: " + order.getCustomer().getStreetName() +
                    "\n" + "Huisnummer: " + order.getCustomer().getHouseNumber() + order.getCustomer().getAdditionalToHouseNumber()
                    + "\n" + " Plaatsnaam: " + order.getCustomer().getCity() + "\n" +
                    "postcode: " + order.getCustomer().getZipcode()
                    , fontAddress);
            addressInformation.setAlignment(Paragraph.ALIGN_RIGHT);

            document.add(invoiceTitle);
            document.add(paymentInformationTitle);
            document.add(paymentInformation);
            document.add(productTitle);
            document.add(products);
            document.add(addressInformation);
            document.close();
        }
    }

    public void returnsInvoice (HttpServletResponse response, Long returnsId) throws IOException {
        if(returnsRepository.findById(returnsId).isPresent()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            Returns returns = returnsRepository.findById(returnsId).orElseThrow();
            Admin admin = adminRepository.findAll().get(0);
            document.open();
            Font fontTitle = FontFactory.getFont(FontFactory.TIMES_BOLD);
            fontTitle.setSize(24);

            Customer customer = returns.getCustomerOrder().getCustomer();

            Paragraph invoiceTitle = new Paragraph("Retouren van " + customer.getFirstName() + " " + customer.getLastName(), fontTitle);
            invoiceTitle.setAlignment(Paragraph.ALIGN_CENTER);

            Font fontAddress = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            fontAddress.setSize(14);

            Font fontProductTitle = FontFactory.getFont(FontFactory.TIMES_BOLD);
            fontProductTitle.setSize(16);

            Font fontProducts = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            fontProducts.setSize(12);

            if(customer.getAdditionalToHouseNumber() == null) {
                customer.setAdditionalToHouseNumber("");
            }

            Paragraph paymentInformationTitle = new Paragraph("RETURN INFORMATION"  + "\n" + "Returns Number: " + returns.getId(), fontProductTitle);

            Paragraph paymentInformation = new Paragraph("Send the products below to the following address " + "\n" +
                    "WebshopStreet 7, 7777WS, Amsterdam" + "\n" +
                    "send a card with your return number with the products" + "\n" +
                    "Once we receive the products we will send the money to the following bankaccount" + "\n"
                    + returns.getBankAccountForReturn() +
                     "The total price of your returns: € " + returns.getTotalPrice() + "\n" + "We will process your returns within 7 working days after receiving the products!" +
                    "\n" + "\n");

            Paragraph productTitle = new Paragraph("RETURNED PRODUCTS", fontProductTitle);
            productTitle.setAlignment(Paragraph.ALIGN_CENTER);

            String returnsList = "";
            for (int i = 0; i < returns.getQuantityAndProductList().size(); i++) {
                returnsList = returnsList + "product: " + returns.getQuantityAndProductList().get(i).getProduct().getProductName() + "         |         " + "Price per piece: " +
                        returns.getQuantityAndProductList().get(i).getProduct().getPrice() + " | " +
                        "Amount: " + returns.getQuantityAndProductList().get(i).getAmountOfReturningProducts() + " | " +
                        " Total price of returns: " + returns.getTotalPrice() + "\n";
            }
            Paragraph products = new Paragraph(returnsList, fontProducts);
            products.setAlignment(Paragraph.ALIGN_CENTER);

            Paragraph addressInformation = new Paragraph("\n" + "\n" + "AddressDetails: " + "\n" +
                    " Straatnaam: " + customer.getStreetName() +
                    "\n" + "Huisnummer: " + customer.getHouseNumber() + customer.getAdditionalToHouseNumber()
                    + "\n" + " Plaatsnaam: " + customer.getCity() + "\n" +
                    "postcode: " + customer.getZipcode()
                    , fontAddress);
            addressInformation.setAlignment(Paragraph.ALIGN_RIGHT);

            document.add(invoiceTitle);
            document.add(paymentInformationTitle);
            document.add(paymentInformation);
            document.add(productTitle);
            document.add(products);
            document.add(addressInformation);
            document.close();
        }
    }
}
