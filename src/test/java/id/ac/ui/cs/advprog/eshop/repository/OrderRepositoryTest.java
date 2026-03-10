package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {

    private static final String AUTHOR = "Safira Sudrajat";
    private static final String INVALID_ID = "zczc";

    OrderRepository orderRepository;
    List<Order> orders;

    @BeforeEach
    void setUp() {

        orderRepository = new OrderRepository();

        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test");
        product.setProductQuantity(1);

        products.add(product);

        orders = new ArrayList<>();

        orders.add(new Order("1", products, 1708560000L, AUTHOR));
        orders.add(new Order("2", products, 1708560000L, AUTHOR));
        orders.add(new Order("3", products, 1708560000L, "Bambang Sudrajat"));
    }

    @Test
    void testFindByIdIfIdFound() {

        for (Order order : orders) {
            orderRepository.save(order);
        }

        Order result = orderRepository.findById("1");

        assertEquals("1", result.getId());
    }

    @Test
    void testFindByIdIfIdNotFound() {

        Order result = orderRepository.findById(INVALID_ID);

        assertNull(result);
    }

    @Test
    void testFindAllByAuthorIfAuthorCorrect() {

        for (Order order : orders) {
            orderRepository.save(order);
        }

        List<Order> result = orderRepository.findAllByAuthor(AUTHOR);

        assertEquals(2, result.size());
    }

}