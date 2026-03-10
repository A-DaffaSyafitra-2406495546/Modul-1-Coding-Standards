package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private Order order;
    private Payment payment;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        order = new Order("order-1", products, System.currentTimeMillis(), "user");
    }

    @Test
    void testPaymentCreation() {
        String method = "BANK_TRANSFER";
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "ABC123");

        payment = new Payment("1", order.getId(), method, paymentData);

        assertEquals("1", payment.getId());
        assertEquals(order.getId(), payment.getOrderId());
        assertEquals(method, payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals("PENDING", payment.getStatus());
    }

    @Test
    void testPaymentWithVoucherCode() {
        String method = "VOUCHER";
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        payment = new Payment("2", order.getId(), method, paymentData);

        assertEquals("2", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertTrue(paymentData.containsKey("voucherCode"));
    }

    @Test
    void testPaymentWithCashOnDelivery() {
        String method = "CASH_ON_DELIVERY";
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Merdeka 123");
        paymentData.put("deliveryFee", "50000");

        payment = new Payment("3", order.getId(), method, paymentData);

        assertEquals("3", payment.getId());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
        assertTrue(paymentData.containsKey("address"));
    }
}
