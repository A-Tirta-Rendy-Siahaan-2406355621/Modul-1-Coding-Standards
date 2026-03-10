package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceImplTest {

    PaymentServiceImpl service;

    private static final String AUTHOR = "Safira Sudrajat";

    @BeforeEach
    void setup(){
        service = new PaymentServiceImpl(new PaymentRepository());
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
                AUTHOR
        );
    }

    @Test
    void testVoucherSuccess(){

        Order order = createOrder();

        Map<String,String> data = new HashMap<>();
        data.put("voucherCode","ESHOP1234ABC5678");

        var payment = service.addPayment(order,"VOUCHER",data);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testVoucherRejected(){

        Order order = createOrder();

        Map<String,String> data = new HashMap<>();
        data.put("voucherCode","INVALID");

        var payment = service.addPayment(order,"VOUCHER",data);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCODSuccess(){

        Order order = createOrder();

        Map<String,String> data = new HashMap<>();
        data.put("address","Depok");
        data.put("deliveryFee","10000");

        var payment = service.addPayment(order,"COD",data);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCODRejected(){

        Order order = createOrder();

        Map<String,String> data = new HashMap<>();
        data.put("address","");
        data.put("deliveryFee","10000");

        var payment = service.addPayment(order,"COD",data);

        assertEquals("REJECTED", payment.getStatus());
    }
}