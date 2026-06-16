package kr.com.brorder.menu.controller;

import kr.com.brorder.menu.model.Menu;
import kr.com.brorder.menu.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
                          Model model) {

        model.addAttribute("storeId", storeId);

        return "menu/add";
    }

    // 메뉴 등록 처리
    @PostMapping("/menu/add")
    public String addMenu(Menu menu,
                          @RequestParam("file") MultipartFile file)
            throws IOException {

        menuService.insertMenu(menu, file);

        return "redirect:/store/" + menu.getStoreId();
    }

    // 메뉴 목록
    @GetMapping("/menu/list")
    public String menuList(@RequestParam Integer storeId,
                           Model model) {

        List<Menu> menuList =
                menuService.selectMenuListByStoreId(storeId);

        model.addAttribute("menuList", menuList);

        return "menu/list";
    }

    @PostMapping("/menu/{menuId}/delete")
    public String deleteMenu(@PathVariable Integer menuId) {

        System.out.println("삭제 요청 들어옴");

        Menu menu = menuService.selectMenuById(menuId);

        System.out.println("메뉴 조회 완료");

        Integer storeId = menu.getStoreId();

        menuService.deleteMenu(menuId);

        System.out.println("삭제 완료");

        return "redirect:/store/" + storeId;
    }
}