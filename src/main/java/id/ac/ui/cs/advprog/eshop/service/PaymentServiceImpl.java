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

    public PaymentRepository paymentRepository = new PaymentRepository();

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

    private void validatePayment(Payment payment){

        if(payment.getMethod().equals("VOUCHER")){

            String code = payment.getPaymentData().get("voucherCode");

            if(code != null && code.length() == 16 && code.startsWith("ESHOP")){

                int digitCount = 0;

                for(char c : code.toCharArray()){
                    if(Character.isDigit(c)){
                        digitCount++;
                    }
                }

                if(digitCount == 8){
                    payment.setStatus("SUCCESS");
                    payment.getOrder().setStatus("SUCCESS");
                    return;
                }
            }

            payment.setStatus("REJECTED");
            payment.getOrder().setStatus("FAILED");
        }

        if(payment.getMethod().equals("COD")){

            String address = payment.getPaymentData().get("address");
            String deliveryFee = payment.getPaymentData().get("deliveryFee");

            if(address == null || address.isEmpty() ||
                    deliveryFee == null || deliveryFee.isEmpty()){

                payment.setStatus("REJECTED");
                payment.getOrder().setStatus("FAILED");

            }else{

                payment.setStatus("SUCCESS");
                payment.getOrder().setStatus("SUCCESS");
            }
        }
    }

    @Override
    public Payment setStatus(Payment payment, String status){

        payment.setStatus(status);

        if(status.equals("SUCCESS")){
            payment.getOrder().setStatus("SUCCESS");
        }

        if(status.equals("REJECTED")){
            payment.getOrder().setStatus("FAILED");
        }

        return payment;
    }

    @Override
    public Payment getPayment(String paymentId){
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }
}