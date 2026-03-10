package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceImplTest {

    PaymentServiceImpl service;

    @BeforeEach
    void setup(){
        service = new PaymentServiceImpl();
        service.paymentRepository = new PaymentRepository();
    }

    private Order createOrder(){

        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(1);

        products.add(product);

        return new Order(
                "1",
                products,
                1700000000L,
                "Safira Sudrajat"
        );
    }

    @Test
    void testAddPaymentVoucherSuccess(){

        Order order = createOrder();

        Map<String,String> data = new HashMap<>();
        data.put("voucherCode","ESHOP1234ABC5678");

        Payment payment = service.addPayment(order,"VOUCHER",data);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testAddPaymentVoucherRejected(){

        Order order = createOrder();

        Map<String,String> data = new HashMap<>();
        data.put("voucherCode","INVALID");

        Payment payment = service.addPayment(order,"VOUCHER",data);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCashOnDeliverySuccess(){

        Order order = createOrder();

        Map<String,String> data = new HashMap<>();
        data.put("address","Depok");
        data.put("deliveryFee","15000");

        Payment payment = service.addPayment(order,"COD",data);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCashOnDeliveryRejected(){

        Order order = createOrder();

        Map<String,String> data = new HashMap<>();
        data.put("address","");
        data.put("deliveryFee","15000");

        Payment payment = service.addPayment(order,"COD",data);

        assertEquals("REJECTED", payment.getStatus());
    }
}