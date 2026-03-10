package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final String KEY_VOUCHER_CODE = "voucherCode";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_DELIVERY_FEE = "deliveryFee";

    private static final int VOUCHER_LENGTH = 16;
    private static final int VOUCHER_DIGIT_COUNT = 8;

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {

        Payment payment = new Payment();

        payment.setId(UUID.randomUUID().toString());
        payment.setOrder(order);
        payment.setMethod(method);
        payment.setPaymentData(paymentData);

        validatePayment(payment);

        return paymentRepository.save(payment);
    }

    private void validatePayment(Payment payment) {

        if (PaymentMethod.VOUCHER.getValue().equals(payment.getMethod())) {
            validateVoucherPayment(payment);
        }

        if (PaymentMethod.CASH_ON_DELIVERY.getValue().equals(payment.getMethod())) {
            validateCODPayment(payment);
        }
    }

    private void validateVoucherPayment(Payment payment) {

        String code = payment.getPaymentData().get(KEY_VOUCHER_CODE);

        if (code != null && code.length() == VOUCHER_LENGTH && code.startsWith("ESHOP")) {

            int digitCount = 0;

            for (char c : code.toCharArray()) {
                if (Character.isDigit(c)) {
                    digitCount++;
                }
            }

            if (digitCount == VOUCHER_DIGIT_COUNT) {
                setSuccess(payment);
                return;
            }
        }

        setRejected(payment);
    }

    private void validateCODPayment(Payment payment) {

        String address = payment.getPaymentData().get(KEY_ADDRESS);
        String deliveryFee = payment.getPaymentData().get(KEY_DELIVERY_FEE);

        boolean invalidAddress = address == null || address.isEmpty();
        boolean invalidFee = deliveryFee == null || deliveryFee.isEmpty();

        if (invalidAddress || invalidFee) {
            setRejected(payment);
        } else {
            setSuccess(payment);
        }
    }

    private void setSuccess(Payment payment) {
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        payment.getOrder().setStatus(PaymentStatus.SUCCESS.getValue());
    }

    private void setRejected(Payment payment) {
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        payment.getOrder().setStatus(PaymentStatus.FAILED.getValue());
    }

    @Override
    public Payment setStatus(Payment payment, String status) {

        payment.setStatus(status);

        if (PaymentStatus.SUCCESS.getValue().equals(status)) {
            payment.getOrder().setStatus(PaymentStatus.SUCCESS.getValue());
        }

        if (PaymentStatus.REJECTED.getValue().equals(status)) {
            payment.getOrder().setStatus(PaymentStatus.FAILED.getValue());
        }

        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId)
                .orElse(null);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}