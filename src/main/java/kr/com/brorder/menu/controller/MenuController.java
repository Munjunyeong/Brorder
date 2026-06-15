package kr.com.brorder.menu.controller;

import kr.com.brorder.menu.model.Menu;
import kr.com.brorder.menu.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/menu/add")
    public String addMenu(Menu menu,
                          @RequestParam("file") MultipartFile file) throws IOException {

        menuService.insertMenu(menu, file);

        return "redirect:/menu/list";
    }

    @GetMapping("/menu/list")
    public String menuList(@RequestParam Integer storeId, Model model) {

        List<Menu> menuList = menuService.selectMenuListByStoreId(storeId);

        model.addAttribute("menuList", menuList);

        return "menu/list";
    }
}