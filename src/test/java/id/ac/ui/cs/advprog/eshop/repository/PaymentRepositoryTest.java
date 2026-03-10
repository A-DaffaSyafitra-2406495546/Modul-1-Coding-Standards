package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Payment payment;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "ABC123");
        payment = new Payment("1", "order-1", "BANK_TRANSFER", paymentData);
    }

    @Test
    void testSavePayment() {
        paymentRepository.save(payment);
        assertNotNull(paymentRepository.findById(payment.getId()));
    }

    @Test
    void testFindPaymentById() {
        paymentRepository.save(payment);
        Payment foundPayment = paymentRepository.findById("1");

        assertNotNull(foundPayment);
        assertEquals("1", foundPayment.getId());
        assertEquals("order-1", foundPayment.getOrderId());
    }

    @Test
    void testFindPaymentByIdNotFound() {
        Payment foundPayment = paymentRepository.findById("nonexistent");
        assertNull(foundPayment);
    }

    @Test
    void testFindAllPayments() {
        paymentRepository.save(payment);
        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("address", "Jl. Sudirman");
        paymentData2.put("deliveryFee", "30000");
        Payment payment2 = new Payment("2", "order-2", "CASH_ON_DELIVERY", paymentData2);
        paymentRepository.save(payment2);

        var allPayments = paymentRepository.findAll();
        assertEquals(2, allPayments.size());
    }
}

