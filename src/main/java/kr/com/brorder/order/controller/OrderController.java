package kr.com.brorder.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import kr.com.brorder.order.model.Order;
import kr.com.brorder.order.service.OrderService;
import kr.com.brorder.store.StoreService;
import kr.com.brorder.menu.service.MenuService;
import kr.com.brorder.menu.model.Menu;
import kr.com.brorder.users.Users;
import kr.com.brorder.users.UsersService;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    // 1. 주문 목록 페이지
    @GetMapping
    public String list(HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("users");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("list", orderService.list(user.getUserid()));
        return "order/list";
    }

    // 2. 주문 상세 페이지
    @GetMapping("/{orderId:\\d+}")
    public String item(@PathVariable Long orderId, HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("users");
        if (user == null) {
            return "redirect:/login";
        }
        Order order = orderService.item(orderId);
        if (order != null && order.getUserId() != user.getUserid().intValue()) {
            return "redirect:/orders";
        }
        model.addAttribute("order", order);
        return "order/item";
    }

    // 3. 주문 생성 담당
    @PostMapping
    public String insert(@ModelAttribute Order order, HttpSession session) {
        Users user = (Users) session.getAttribute("users");
        if (user == null) {
            return "redirect:/login";
        }
        order.setUserId(user.getUserid().intValue());
        orderService.insert(order);
        session.removeAttribute("cart");
        return "redirect:/orders";
    }

    // 4. 주문 추가 및 장바구니 관리 (에러 방지 로직 포함)
    @GetMapping("/add")
    public String add(@RequestParam(required = false) String storeId,
                      @RequestParam(required = false) Integer menuId,
                      HttpSession session,
                      Model model) {

        // 안전한 숫자 변환 (detail 문자열 대응)
        Integer sId = (storeId != null && storeId.matches("\\d+")) ? Integer.parseInt(storeId) : null;

        if (sId != null) {
            model.addAttribute("store", storeService.getStoreById(sId));

            List<Menu> cart = (List<Menu>) session.getAttribute("cart");
            if (cart == null) cart = new ArrayList<>();

            if (menuId != null) {
                Menu menu = menuService.selectMenuById(menuId);
                if (menu != null) {
                    cart.add(menu);
                    session.setAttribute("cart", cart);
                }
            }
            model.addAttribute("menuList", cart);

            Users user = (Users) session.getAttribute("users");
            if (user != null) {
                model.addAttribute("addressList", usersService.list(user.getUserid()));
                model.addAttribute("user", user);
            }
        }
        return "order/add";
    }

    // 5. 주문 삭제
    @GetMapping("/delete/{orderId:\\d+}")
    public String delete(@PathVariable Long orderId, HttpSession session) {
        Users user = (Users) session.getAttribute("users");
        if (user == null) return "redirect:/login";

        Order order = orderService.item(orderId);
        if (order != null && order.getUserId() == user.getUserid().intValue()) {
            orderService.delete(orderId);
        }
        return "redirect:/orders";
    }

    // 6. 장바구니 메뉴 삭제 (추가된 기능)
    @GetMapping("/cart/remove")
    public String removeCartItem(@RequestParam int index, @RequestParam(required = false) String storeId, HttpSession session) {
        List<Menu> cart = (List<Menu>) session.getAttribute("cart");
        if (cart != null && index >= 0 && index < cart.size()) {
            cart.remove(index);
            session.setAttribute("cart", cart);
        }
        return "redirect:/orders/add" + (storeId != null ? "?storeId=" + storeId : "");
    }

    // 7. 장바구니 추가 API
    @PostMapping("/cart/add")
    @ResponseBody
    public String addToCart(@RequestBody List<Map<String, Object>> items, HttpSession session) {
        session.setAttribute("cart", items);
        return "success";
    }

    // 8. 장바구니 조회
    @GetMapping("/cart")
    public String cartView(HttpSession session, Model model) {
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        model.addAttribute("cart", cart);
        return "order/cart";
    }
}