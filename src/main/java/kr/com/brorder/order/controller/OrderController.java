package kr.com.brorder.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import kr.com.brorder.order.dto.request.OrderCreateRequest;
import kr.com.brorder.order.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문 목록 페이지
    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "list",
                orderService.list()
        );

        return "order/list";
    }

    // 주문 상세 페이지
    @GetMapping("/{orderId}")
    public String item(@PathVariable Long orderId,
                       Model model) {

        model.addAttribute(
                "order",
                orderService.item(orderId)
        );

        return "order/item";
    }

    // 주문 생성
    @PostMapping
    public String insert(OrderCreateRequest request) {

        orderService.insert(request);

        return "redirect:/orders";
    }

    @GetMapping("/add")
    public String add() {
        return "order/add";
    }

    @GetMapping("/delete/{orderId}")
    public String delete(@PathVariable Long orderId) {

        orderService.delete(orderId);

        return "redirect:/orders";
    }
}
