package com.vanlang.webbanhang.controller;

import com.vanlang.webbanhang.model.Order;
import com.vanlang.webbanhang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @GetMapping("/edit/{orderId}")
    public String editOrder(@PathVariable("orderId") Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        return "admin/edit-order";
    }

    @PostMapping("/update-status/{orderId}")
    public String updateOrderStatus(@PathVariable("orderId") Long orderId, String status) {
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/admin/orders";
    }
    @PostMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId); // Xoá đơn hàng qua OrderService
        return "redirect:/admin/orders"; // Chuyển hướng về trang quản lý đơn hàng
    }
}
