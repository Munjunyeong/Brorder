package kr.com.brorder.menu.controller;

import kr.com.brorder.menu.model.Menu;
import kr.com.brorder.menu.model.MenuOption;
import kr.com.brorder.menu.service.MenuService;
import kr.com.brorder.users.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // 메뉴 등록 화면
    @GetMapping("/menu/add")
    public String addForm(@RequestParam Integer storeId,
                          HttpSession session,
                          Model model) {

        Users user = (Users) session.getAttribute("users");
        if (user == null || !("OWNER".equalsIgnoreCase(user.getRole()))) {
            return "redirect:/login";
        }

        model.addAttribute("storeId", storeId);
        return "menu/add";
    }

    // 메뉴 등록 처리
    @PostMapping("/menu/add")
    public String addMenu(Menu menu,
                          @RequestParam("file") MultipartFile file,
                          HttpSession session) throws IOException {

        Users user = (Users) session.getAttribute("users");
        if (user == null || !("OWNER".equalsIgnoreCase(user.getRole()))) {
            return "redirect:/login";
        }

        menuService.insertMenu(menu, file);
        return "redirect:/store/" + menu.getStoreId();
    }

    // 메뉴 목록
    @GetMapping("/menu/list")
    public String menuList(@RequestParam Integer storeId,
                           Model model) {

        List<Menu> menuList = menuService.selectMenuListByStoreId(storeId);

        model.addAttribute("menuList", menuList);
        model.addAttribute("storeId", storeId);

        return "menu/list";
    }

    // 메뉴 수정 화면
    @GetMapping("/menu/{menuId}/update")
    public String updateForm(@PathVariable Integer menuId,
                             HttpSession session,
                             Model model) {

        Users user = (Users) session.getAttribute("users");
        if (user == null || !("OWNER".equalsIgnoreCase(user.getRole()))) {
            return "redirect:/login";
        }

        Menu menu = menuService.selectMenuById(menuId);
        if (menu == null) {
            return "redirect:/store/list";
        }

        model.addAttribute("menu", menu);
        return "menu/update";
    }

    @PostMapping("/menu/{menuId}/update")
    public String updateMenu(@PathVariable Integer menuId,
                             Menu menu,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             HttpSession session) throws IOException {

        Users user = (Users) session.getAttribute("users");
        if (user == null || !("OWNER".equalsIgnoreCase(user.getRole()))) {
            return "redirect:/login";
        }

        Menu origin = menuService.selectMenuById(menuId);
        if (origin == null) {
            return "redirect:/store/list";
        }

        menu.setMenuId(menuId);
        menu.setStoreId(origin.getStoreId());

        menuService.updateMenu(menu, file);

        return "redirect:/store/" + origin.getStoreId();
    }

    // 메뉴 삭제
    @PostMapping("/menu/{menuId}/delete")
    public String deleteMenu(@PathVariable Integer menuId,
                             HttpSession session) {

        Users user = (Users) session.getAttribute("users");
        if (user == null || !("OWNER".equalsIgnoreCase(user.getRole()))) {
            return "redirect:/login";
        }

        Menu menu = menuService.selectMenuById(menuId);
        if (menu == null) {
            return "redirect:/store/list";
        }
        Integer storeId = menu.getStoreId();

        menuService.deleteMenu(menuId);

        return "redirect:/store/" + storeId;
    }

    @GetMapping("/api/menus/{menuId}/options")
    @ResponseBody
    public List<MenuOption> getOptions(@PathVariable Integer menuId) {

        return menuService.selectOptionsByMenuId(menuId);
    }
}