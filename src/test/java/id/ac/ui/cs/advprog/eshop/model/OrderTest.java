package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private static final String ORDER_ID = "13652556-012a-4c07-b546-54eb1396d79b";
    private static final String AUTHOR = "Safira Sudrajat";
    private static final long ORDER_TIME = 1708560000L;

    private List<Product> createProducts() {
        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(1);

        products.add(product);
        return products;
    }

    @Test
    void testCreateOrderDefaultStatus() {

        Order order = new Order(
                ORDER_ID,
                createProducts(),
                ORDER_TIME,
                AUTHOR
        );

        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    @Test
    void testCreateOrderSuccessStatus() {

        Order order = new Order(
                ORDER_ID,
                createProducts(),
                ORDER_TIME,
                AUTHOR,
                "SUCCESS"
        );

        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testCreateOrderInvalidStatus() {

        assertThrows(IllegalArgumentException.class, () ->
                new Order(
                        ORDER_ID,
                        createProducts(),
                        ORDER_TIME,
                        AUTHOR,
                        "INVALID"
                )
        );
    }

    @Test
    void testSetStatusToCancelled() {

        Order order = new Order(
                ORDER_ID,
                createProducts(),
                ORDER_TIME,
                AUTHOR
        );

        order.setStatus("CANCELLED");

        assertEquals("CANCELLED", order.getStatus());
    }

}