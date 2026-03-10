package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, order.getId(), method, paymentData);
        
        // Validate payment data based on method
        if ("BANK_TRANSFER".equals(method)) {
            validateBankTransferPayment(payment);
        } else if ("CASH_ON_DELIVERY".equals(method)) {
            validateCashOnDeliveryPayment(payment);
        } else if ("VOUCHER".equals(method)) {
            validateVoucherPayment(payment);
        }
        
        return paymentRepository.save(payment);
    }

    private void validateBankTransferPayment(Payment payment) {
        String bankName = payment.getPaymentData().get("bankName");
        String referenceCode = payment.getPaymentData().get("referenceCode");

        if (isEmpty(bankName) || isEmpty(referenceCode)) {
            payment.setStatus("REJECTED");
        }
    }

    private void validateCashOnDeliveryPayment(Payment payment) {
        String address = payment.getPaymentData().get("address");
        String deliveryFee = payment.getPaymentData().get("deliveryFee");

        if (isEmpty(address) || isEmpty(deliveryFee)) {
            payment.setStatus("REJECTED");
        }
    }

    private void validateVoucherPayment(Payment payment) {
        String voucherCode = payment.getPaymentData().get("voucherCode");

        if (isEmpty(voucherCode)) {
            payment.setStatus("REJECTED");
        } else if (!isValidVoucherCode(voucherCode)) {
            payment.setStatus("REJECTED");
        }
    }

    private boolean isValidVoucherCode(String voucherCode) {
        // Voucher code must be 16 characters long, start with "ESHOP", and contain 8 numerical characters
        if (voucherCode == null || voucherCode.length() != 16) {
            return false;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        // Count numerical characters in the substring after "ESHOP"
        String substring = voucherCode.substring(5);
        long numCount = substring.chars().filter(Character::isDigit).count();
        
        return numCount == 8;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
