package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {

    Product create(Product product);

    List<Product> findAll();

    void delete(String productName);

    Product findByName(String productName);

    void update(String oldName, String newName, int newQuantity);

}
