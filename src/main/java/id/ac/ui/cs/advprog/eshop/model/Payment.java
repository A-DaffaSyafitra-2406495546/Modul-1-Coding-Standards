package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Payment {
    private String id;
    private String orderId;
    private String method;
    private Map<String, String> paymentData;
    private String status;

    public Payment() {
    }

    public Payment(String id, String orderId, String method, Map<String, String> paymentData) {
        this.id = id;
        this.orderId = orderId;
        this.method = method;
        this.paymentData = paymentData;
        this.status = "PENDING";
    }
}
