package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceImplTest {

    PaymentServiceImpl service;

    @BeforeEach
    void setup(){
        service = new PaymentServiceImpl();
        service.paymentRepository = new PaymentRepository();
    }

    @Test
    void testAddPayment(){
        Order order = new Order();
        order.setId("1");

        Map<String,String> data = new HashMap<>();
        data.put("voucherCode","ESHOP1234ABC5678");

        Payment payment = service.addPayment(order,"VOUCHER",data);

        assertNotNull(payment);
    }
}