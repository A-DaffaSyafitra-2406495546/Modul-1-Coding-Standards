package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class EditProductTest {

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

        Product product = new Product();
        product.setProductName("Sampo");
        product.setProductQuantity(10);

        productRepository.create(product);
    }

    @Test
    void editProduct_positive_existingProduct() {
        productService.update("Sampo", "Sampo Baru", 20);

        Product updatedProduct = productService.findByName("Sampo Baru");

        assertNotNull(updatedProduct);
        assertEquals("Sampo Baru", updatedProduct.getProductName());
        assertEquals(20, updatedProduct.getProductQuantity());
    }

    @Test
    void editProduct_negative_productNotFound() {
        productService.update("TidakAda", "Produk Baru", 5);

        assertEquals(1, productService.findAll().size());
        assertNull(productService.findByName("Produk Baru"));
    }
}
