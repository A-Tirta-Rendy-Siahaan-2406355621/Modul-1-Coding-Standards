package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final List<Product> productData = new ArrayList<>();

    @Override
    public Product create(Product product) {
        if (product == null) return null;

        if (product.getProductId() == null || product.getProductId().isBlank()) {
            product.setProductId(UUID.randomUUID().toString());
        }
        productData.add(product);
        return product;
    }

    @Override
    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    @Override
    public boolean delete(String id) {
        if (id == null || id.isBlank()) return false;
        return productData.removeIf(p -> id.equals(p.getProductId()));
    }

    @Override
    public Product findById(String id) {
        if (id == null || id.isBlank()) return null;

        return productData.stream()
                .filter(p -> id.equals(p.getProductId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(Product updated) {
        if (!isUpdatable(updated)) {
            return false;
        }

        for (Product current : productData) {
            if (updated.getProductId().equals(current.getProductId())) {
                updateFields(current, updated);
                return true;
            }
        }
        return false;
    }


    private boolean isUpdatable(Product product) {
        return product != null
                && product.getProductId() != null
                && !product.getProductId().isBlank();
    }


    private void updateFields(Product target, Product source) {
        target.setProductName(source.getProductName());
        target.setProductQuantity(source.getProductQuantity());
    }
}