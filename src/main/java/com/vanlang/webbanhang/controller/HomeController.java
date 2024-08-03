package com.vanlang.webbanhang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        // Thêm bất kỳ model attribute nào nếu cần
        return "admin/home";  // Trả về template home.html trong thư mục admin
    }
}