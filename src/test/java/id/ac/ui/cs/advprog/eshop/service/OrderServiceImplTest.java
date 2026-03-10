package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private static final String AUTHOR = "Safira Sudrajat";
    private static final String INVALID_ID = "zczc";

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    List<Order> orders;

    @BeforeEach
    void setUp() {

        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test");
        product.setProductQuantity(1);

        products.add(product);

        orders = new ArrayList<>();

        orders.add(new Order("1", products, 1708560000L, AUTHOR));
        orders.add(new Order("2", products, 1708560000L, AUTHOR));
    }

    @Test
    void testFindByIdIfIdFound() {

        Order order = orders.get(0);

        when(orderRepository.findById(order.getId())).thenReturn(order);

        Order result = orderService.findById(order.getId());

        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testFindByIdIfIdNotFound() {

        when(orderRepository.findById(INVALID_ID)).thenReturn(null);

        Order result = orderService.findById(INVALID_ID);

        assertNull(result);
    }

}