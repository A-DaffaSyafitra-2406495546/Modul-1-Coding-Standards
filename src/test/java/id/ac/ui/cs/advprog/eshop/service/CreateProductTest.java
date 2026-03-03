package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class CreateProductTest {

    private ProductServiceImpl productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        productService = new ProductServiceImpl();

        ReflectionTestUtils.setField(productService, "productRepository", productRepository);
    }

    @Test
    void createProduct_positive_shouldBeSaved() {
        Product product = new Product();
        product.setProductName("Sabun");
        product.setProductQuantity(5);

        Product returned = productService.create(product);

        // baris return product; di create() jadi ke-cover
        assertSame(product, returned);

        // pastiin beneran masuk repository via service
        assertEquals(1, productService.findAll().size());
        Product saved = productService.findByName("Sabun");
        assertNotNull(saved);
        assertEquals("Sabun", saved.getProductName());
        assertEquals(5, saved.getProductQuantity());
    }

    @Test
    void createProduct_positive_multipleProducts() {
        Product p1 = new Product();
        p1.setProductName("A");
        p1.setProductQuantity(1);

        Product p2 = new Product();
        p2.setProductName("B");
        p2.setProductQuantity(2);

        productService.create(p1);
        productService.create(p2);

        assertEquals(2, productService.findAll().size());
        assertNotNull(productService.findByName("A"));
        assertNotNull(productService.findByName("B"));
    }
}