package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@TestPropertySource(properties = "spring.thymeleaf.check-template-location=false")
class ProductControllerTest {

    private static final String BASE = "/product";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void createPage_shouldReturnCreateProductView() throws Exception {
        mockMvc.perform(get(BASE + "/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void createPost_shouldCallServiceAndRedirect() throws Exception {
        mockMvc.perform(post(BASE + "/create")
                        .param("productName", "Indomie")
                        .param("productQuantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/list"));

        verify(service).create(any(Product.class));
    }

    @Test
    void listPage_shouldShowProducts() throws Exception {
        when(service.findAll()).thenReturn(List.of(new Product()));

        mockMvc.perform(get(BASE + "/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"));

        verify(service).findAll();
    }

    @Test
    void deletePage_shouldReturnDeleteProductView() throws Exception {
        mockMvc.perform(get(BASE + "/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("deleteProduct"));
    }

    @Test
    void deletePost_shouldCallServiceAndRedirect() throws Exception {
        mockMvc.perform(post(BASE + "/delete")
                        .param("productName", "Indomie"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/list"));

        verify(service).delete("Indomie");
    }

    @Test
    void editPage_shouldLoadProductAndReturnEditProductView() throws Exception {
        Product p = new Product();
        when(service.findByName("Indomie")).thenReturn(p);

        mockMvc.perform(get(BASE + "/edit").param("name", "Indomie"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attribute("product", p));

        verify(service).findByName("Indomie");
    }

    @Test
    void editPost_shouldCallUpdateAndRedirect() throws Exception {
        mockMvc.perform(post(BASE + "/edit")
                        .param("oldName", "Indomie")
                        .param("productName", "Indomie Goreng")
                        .param("productQuantity", "7"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/list"));

        verify(service).update("Indomie", "Indomie Goreng", 7);
    }
}