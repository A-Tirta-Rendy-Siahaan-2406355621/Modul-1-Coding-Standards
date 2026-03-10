package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository repository;

    @BeforeEach
    void setup(){
        repository = new PaymentRepository();
    }

    @Test
    void testSavePayment(){
        Payment payment = new Payment();
        payment.setId("1");

        repository.save(payment);

        Payment result = repository.findById("1");

        assertEquals("1", result.getId());
    }
}