package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BankTransferPaymentTest {
    private PaymentServiceImpl paymentService;
    private PaymentRepository paymentRepository;
    private Order order;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        paymentService = new PaymentServiceImpl(paymentRepository);

        List<Product> products = new ArrayList<>();
        products.add(new Product());
        order = new Order("order-1", products, System.currentTimeMillis(), "user");
    }

    @Test
    void testBankTransferWithValidData() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "TRF123456");

        Payment payment = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        // Validate that payment is created with PENDING status
        assertEquals("PENDING", payment.getStatus());
        assertEquals("BCA", payment.getPaymentData().get("bankName"));
        assertEquals("TRF123456", payment.getPaymentData().get("referenceCode"));
    }

    @Test
    void testBankTransferWithEmptyBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "TRF123456");

        Payment payment = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        // Payment status should be REJECTED if bank name is empty
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testBankTransferWithEmptyReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "");

        Payment payment = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        // Payment status should be REJECTED if reference code is empty
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testBankTransferWithNullBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "TRF123456");

        Payment payment = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        // Payment status should be REJECTED if bank name is null
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testBankTransferWithNullReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", null);

        Payment payment = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        // Payment status should be REJECTED if reference code is null
        assertEquals("REJECTED", payment.getStatus());
    }
}
