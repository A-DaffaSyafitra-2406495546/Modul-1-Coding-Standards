package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    List<Order> orders;

    @BeforeEach
    void setUp() {
        orders = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);
    }

    @Test
    void testCreateOrder() {
        Order order = orders.get(0);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertEquals(order.getId(), result.getId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdateStatusSuccess() {
        Order order = orders.get(0);
        when(orderRepository.findById(order.getId())).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.updateStatus(order.getId(), OrderStatus.SUCCESS.getValue());

        assertEquals(OrderStatus.SUCCESS.getValue(), result.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdateStatusInvalid() {
        Order order = orders.get(0);
        when(orderRepository.findById(order.getId())).thenReturn(order);

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.updateStatus(order.getId(), "MEOW");
        });
    }

    @Test
    void testFindByIdFound() {
        Order order = orders.get(0);
        when(orderRepository.findById(order.getId())).thenReturn(order);

        Order result = orderService.findById(order.getId());

        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(orderRepository.findById("id-ngasal")).thenReturn(null);

        Order result = orderService.findById("id-ngasal");

        assertNull(result);
    }

    @Test
    void testFindAllByAuthor() {
        when(orderRepository.findAllByAuthor("Safira Sudrajat")).thenReturn(orders);

        List<Order> result = orderService.findAllByAuthor("Safira Sudrajat");

        assertEquals(1, result.size());
        assertEquals("Safira Sudrajat", result.get(0).getAuthor());
    }
}