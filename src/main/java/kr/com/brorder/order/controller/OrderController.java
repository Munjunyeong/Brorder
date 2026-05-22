package kr.com.brorder.order.controller;

import java.util.List;

import kr.com.brorder.order.dto.request.OrderCreateRequest;
import kr.com.brorder.order.dto.response.OrderDetailResponse;
import org.springframework.web.bind.annotation.*;

import kr.com.brorder.order.model.Order;
import kr.com.brorder.order.service.OrderService;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public void insert(@RequestBody OrderCreateRequest request) {
        orderService.insert(request);
    }

    @GetMapping("/orders")
    public List<Order> list() {
        return orderService.list();
    }

    @GetMapping("/orders/{orderId}")
    public OrderDetailResponse item(@PathVariable Long orderId) {
        return orderService.item(orderId);
    }
}