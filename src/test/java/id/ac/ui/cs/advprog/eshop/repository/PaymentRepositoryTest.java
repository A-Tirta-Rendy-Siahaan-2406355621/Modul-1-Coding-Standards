package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepositoryImpl repository;

    @BeforeEach
    void setup() {
        repository = new PaymentRepositoryImpl();
    }

    @Test
    void testSavePayment() {
        Payment payment = new Payment();
        payment.setId("1");

        repository.save(payment);

        Optional<Payment> result = repository.findById("1");

        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Payment> result = repository.findById("999");

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllPayments() {
        Payment payment1 = new Payment();
        payment1.setId("1");

        Payment payment2 = new Payment();
        payment2.setId("2");

        repository.save(payment1);
        repository.save(payment2);

        List<Payment> payments = repository.findAll();

        assertEquals(2, payments.size());
        assertTrue(payments.contains(payment1));
        assertTrue(payments.contains(payment2));
    }
}