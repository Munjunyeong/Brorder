package kr.com.brorder.order.controller; //오더컨트롤러.

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

    // 주문 상세 페이지
    @GetMapping("/{orderId}")
    public String item(@PathVariable Long orderId,
                       HttpSession session,
                       Model model) {
        Users user = (Users) session.getAttribute("users");
        if (user == null) {
            return "redirect:/login";
        }

        Order order = orderService.item(orderId);
        
        // 본인 주문이 아니면 접근 차단 (관리자 권한이 있다면 예외 가능하지만 여기서는 단순하게 본인 확인)
        if (order != null && order.getUserId() != user.getUserid().intValue()) {
            return "redirect:/orders";
        }

        model.addAttribute(
                "order",
                order
        );

        return "order/item";
    }

    // 주문 생성
    @PostMapping
    public String insert(@ModelAttribute Order order, HttpSession session) {
        Users user = (Users) session.getAttribute("users");
        if (user != null) {
            order.setUserId(user.getUserid().intValue());
        }

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
                // 가게 전체 메뉴 (장바구니 구현 전까지는 전체를 보여줌)
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

    @GetMapping("/delete/{orderId}")
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
