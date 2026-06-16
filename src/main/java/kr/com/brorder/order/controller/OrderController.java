package kr.com.brorder.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import kr.com.brorder.order.model.Order;
import kr.com.brorder.order.service.OrderService;
import kr.com.brorder.store.StoreService;
import kr.com.brorder.store.Store;
import kr.com.brorder.menu.service.MenuService;
import kr.com.brorder.menu.model.Menu;

import kr.com.brorder.users.Address;
import kr.com.brorder.users.Users;
import kr.com.brorder.users.UsersService;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final StoreService storeService;
    private final MenuService menuService;
    private final UsersService usersService;

    public OrderController(OrderService orderService, StoreService storeService, MenuService menuService, UsersService usersService) {
        this.orderService = orderService;
        this.storeService = storeService;
        this.menuService = menuService;
        this.usersService = usersService;
    }

    // 주문 목록 페이지
    @GetMapping
    public String list(HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("users");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "list",
                orderService.list(user.getUserid())
        );

        return "order/list";
    }

    //
    @GetMapping("/{orderId:\\d+}")
    public String item(@PathVariable Long orderId,
                       HttpSession session,
                       Model model) {
        Users user = (Users) session.getAttribute("users");
        if (user == null) {
            return "redirect:/login";
        }

        Order order = orderService.item(orderId);

        // 본인 주문이 아니면 접근 차단
        if (order != null && order.getUserId() != user.getUserid().intValue()) {
            return "redirect:/orders";
        }

        model.addAttribute(
                "order",
                order
        );

        return "order/item";
    }

    // 주문 생성 담당 메서드
    @PostMapping
    public String insert(@ModelAttribute Order order, HttpSession session) {
        // 1. 세션에서 로그인 유저 체크
        Users user = (Users) session.getAttribute("users");
        if (user == null) {
            return "redirect:/login";
        }


        order.setUserId(user.getUserid().intValue());

        // 3. 서비스 호출 및 저장
        orderService.insert(order);

        return "redirect:/orders";
    }

    @GetMapping("/add")
    public String add(@RequestParam(required = false) Integer storeId,
                      @RequestParam(required = false) Integer menuId,
                      HttpSession session,
                      Model model) {
        if (storeId != null) {
            Store store = storeService.getStoreById(storeId);
            model.addAttribute("store", store);

            if (menuId != null) {
                // 특정 메뉴만 선택한 경우
                Menu menu = menuService.selectMenuById(menuId);
                if (menu != null) {
                    model.addAttribute("menuList", List.of(menu));
                } else {
                    model.addAttribute("menuList", List.of());
                }
            } else {
                // 가게 전체 메뉴
                List<Menu> menuList = menuService.selectMenuListByStoreId(storeId);
                model.addAttribute("menuList", menuList);
            }

            // 사용자 주소 정보 추가
            Users user = (Users) session.getAttribute("users");
            if (user != null) {
                List<Address> addressList = usersService.list(user.getUserid());
                model.addAttribute("addressList", addressList);
                model.addAttribute("user", user);
            }
        }
        return "order/add";
    }


    @GetMapping("/delete/{orderId:\\d+}")
    public String delete(@PathVariable Long orderId, HttpSession session) {
        Users user = (Users) session.getAttribute("users");
        if (user == null) {
            return "redirect:/login";
        }

        Order order = orderService.item(orderId);
        if (order != null && order.getUserId() == user.getUserid().intValue()) {
            orderService.delete(orderId);
        }

        return "redirect:/orders";
    }
}