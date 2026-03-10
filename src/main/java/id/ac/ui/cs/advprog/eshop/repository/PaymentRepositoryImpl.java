package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final Map<String, Payment> paymentData = new HashMap<>();

    @Override
    public Payment save(Payment payment) {
        paymentData.put(payment.getId(), payment);
        return payment;
    }

    @Override
    public Optional<Payment> findById(String paymentId) {
        return Optional.ofNullable(paymentData.get(paymentId));
    }

    @Override
    public List<Payment> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(paymentData.values()));
    }
}