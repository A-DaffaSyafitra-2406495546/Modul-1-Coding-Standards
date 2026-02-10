package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;


import static org.junit.jupiter.api.Assertions.*;

class DeleteProductTest {

    private ProductServiceImpl productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        productService = new ProductServiceImpl();

        ReflectionTestUtils.setField(
                productService,
                "productRepository",
                productRepository
        );

        Product p1 = new Product();
        p1.setProductName("Sampo");
        p1.setProductQuantity(10);

        Product p2 = new Product();
        p2.setProductName("Sabun");
        p2.setProductQuantity(5);

        productRepository.create(p1);
        productRepository.create(p2);
    }


    @Test
    void deleteProduct_positive_existingProduct() {
        productService.delete("Sampo");

        assertEquals(1, productService.findAll().size());
        assertNull(productService.findByName("Sampo"));
    }

    @Test
    void deleteProduct_negative_productNotFound() {
        productService.delete("TidakAda");

        assertEquals(2, productService.findAll().size());
    }
}
