package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(new ProductRepositoryImpl());
    }

    @Test
    void testUpdateProduct_positive_idExists_shouldUpdateNameAndQuantity() {
        Product p = new Product();
        p.setProductId("p1");
        p.setProductName("Old Name");
        p.setProductQuantity(10);
        productService.create(p);

        Product updated = new Product();
        updated.setProductId("p1");
        updated.setProductName("New Name");
        updated.setProductQuantity(99);

        assertTrue(productService.update(updated));

        Product result = productService.findById("p1");
        assertNotNull(result);
        assertEquals("New Name", result.getProductName());
        assertEquals(99, result.getProductQuantity());
    }

    @Test
    void testUpdateProduct_negative_idNotExists_shouldNotCreateNewProduct() {
        Product updated = new Product();
        updated.setProductId("does-not-exist");
        updated.setProductName("X");
        updated.setProductQuantity(1);

        assertFalse(productService.update(updated));

        List<Product> all = productService.findAll();
        assertTrue(all.isEmpty());
    }

    @Test
    void testDeleteProduct_positive_idExists_shouldRemoveProduct() {
        Product p = new Product();
        p.setProductId("p1");
        p.setProductName("To Delete");
        p.setProductQuantity(1);
        productService.create(p);

        assertTrue(productService.delete("p1"));

        assertNull(productService.findById("p1"));
        assertTrue(productService.findAll().isEmpty());
    }

    @Test
    void testDeleteProduct_negative_idNotExists_shouldNotAffectExistingProducts() {
        Product p = new Product();
        p.setProductId("p1");
        p.setProductName("Keep Me");
        p.setProductQuantity(5);
        productService.create(p);

        assertFalse(productService.delete("does-not-exist"));

        Product stillThere = productService.findById("p1");
        assertNotNull(stillThere);
        assertEquals("Keep Me", stillThere.getProductName());
        assertEquals(5, stillThere.getProductQuantity());
        assertEquals(1, productService.findAll().size());
    }

    @Test
    void testUpdateProduct_negative_updatedProductNull_shouldDoNothing() {
        assertFalse(productService.update(null));
        assertTrue(productService.findAll().isEmpty());
    }

    @Test
    void testUpdateProduct_negative_productIdNull_shouldDoNothing() {
        Product updated = new Product();
        updated.setProductId(null);
        updated.setProductName("X");
        updated.setProductQuantity(1);

        assertFalse(productService.update(updated));
        assertTrue(productService.findAll().isEmpty());
    }

    @Test
    void testDeleteProduct_negative_idNull_shouldDoNothing() {
        assertFalse(productService.delete(null));
        assertTrue(productService.findAll().isEmpty());
    }
}