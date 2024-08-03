package com.vanlang.webbanhang.service;

import com.vanlang.webbanhang.model.CartItem;
import com.vanlang.webbanhang.model.Order;
import com.vanlang.webbanhang.model.OrderDetail;
import com.vanlang.webbanhang.repository.OrderDetailRepository;
import com.vanlang.webbanhang.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    private final CartService cartService;

    @Transactional
    public Order createOrder(String customerName, List<CartItem> cartItems) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setStatus("PENDING"); // Set initial status to PENDING
        order = orderRepository.save(order);
        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
        }
        cartService.clearCart();
        return order;
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }
    }




    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public void deleteOrder(Long orderId) {
        // Xóa các chi tiết đơn hàng liên quan
        orderDetailRepository.deleteByOrderId(orderId);

        // Xóa đơn hàng
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
        }
    }


}
