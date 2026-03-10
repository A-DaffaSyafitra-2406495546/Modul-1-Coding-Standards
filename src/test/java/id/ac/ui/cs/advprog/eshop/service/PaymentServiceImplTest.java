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

public class PaymentServiceImplTest {
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
    void testAddPaymentWithBankTransfer() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "ABC123");

        Payment payment = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        assertNotNull(payment);
        assertEquals("order-1", payment.getOrderId());
        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
    }

    @Test
    void testAddPaymentWithVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = paymentService.addPayment(order, "VOUCHER", paymentData);

        assertNotNull(payment);
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
    }

    @Test
    void testSetPaymentStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "ABC123");

        Payment payment = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);
        Payment updatedPayment = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", updatedPayment.getStatus());
    }

    @Test
    void testGetPayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "ABC123");

        Payment payment = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);
        Payment foundPayment = paymentService.getPayment(payment.getId());

        assertNotNull(foundPayment);
        assertEquals(payment.getId(), foundPayment.getId());
    }

    @Test
    void testGetAllPayments() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("bankName", "BCA");
        paymentData1.put("referenceCode", "ABC123");
        paymentService.addPayment(order, "BANK_TRANSFER", paymentData1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("address", "Jl. Sudirman");
        paymentData2.put("deliveryFee", "30000");
        paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData2);

        List<Payment> allPayments = paymentService.getAllPayments();
        assertEquals(2, allPayments.size());
    }
}
