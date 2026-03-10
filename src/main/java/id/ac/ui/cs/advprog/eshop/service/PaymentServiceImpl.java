package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final String SUCCESS = "SUCCESS";
    private static final String REJECTED = "REJECTED";
    private static final String FAILED = "FAILED";

    private static final String METHOD_VOUCHER = "VOUCHER";
    private static final String METHOD_COD = "COD";

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

        paymentRepository.save(payment);

        return payment;
    }

    private void validatePayment(Payment payment) {

        if (METHOD_VOUCHER.equals(payment.getMethod())) {

            String code = payment.getPaymentData().get(KEY_VOUCHER_CODE);

            if (code != null && code.length() == VOUCHER_LENGTH && code.startsWith("ESHOP")) {

                int digitCount = 0;

                for (char c : code.toCharArray()) {
                    if (Character.isDigit(c)) {
                        digitCount++;
                    }
                }

                if (digitCount == VOUCHER_DIGIT_COUNT) {
                    payment.setStatus(SUCCESS);
                    payment.getOrder().setStatus(SUCCESS);
                    return;
                }
            }

            payment.setStatus(REJECTED);
            payment.getOrder().setStatus(FAILED);
        }

        if (METHOD_COD.equals(payment.getMethod())) {

            String address = payment.getPaymentData().get(KEY_ADDRESS);
            String deliveryFee = payment.getPaymentData().get(KEY_DELIVERY_FEE);

            boolean invalidAddress = address == null || address.isEmpty();
            boolean invalidFee = deliveryFee == null || deliveryFee.isEmpty();

            if (invalidAddress || invalidFee) {
                payment.setStatus(REJECTED);
                payment.getOrder().setStatus(FAILED);
            } else {
                payment.setStatus(SUCCESS);
                payment.getOrder().setStatus(SUCCESS);
            }
        }
    }

    @Override
    public Payment setStatus(Payment payment, String status) {

        payment.setStatus(status);

        if (SUCCESS.equals(status)) {
            payment.getOrder().setStatus(SUCCESS);
        }

        if (REJECTED.equals(status)) {
            payment.getOrder().setStatus(FAILED);
        }

        return payment;
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