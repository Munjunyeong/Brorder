package kr.com.brorder.menu;

import kr.com.brorder.store.Store;
import kr.com.brorder.store.StoreService;
import kr.com.brorder.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/store/{storeId}/menu/{menuId}/options")
public class MenuOptionController {

    @Autowired
    private MenuOptionService menuOptionService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private StoreService storeService;

    // 1. 옵션 관리 메인 화면
    @GetMapping
    public String manageOptions(@PathVariable("storeId") Integer storeId,
                                @PathVariable("menuId") Integer menuId,
                                HttpSession session, Model model) {
        // 보안 검증 (사장님 본인인지 확인)
        Users users = (Users) session.getAttribute("users");
        if (users == null) return "redirect:/login";
        Store store = storeService.getStoreById(storeId);
        if (!"ADMIN".equals(users.getRole()) && !users.getUserid().equals(store.getUserid())) {
            return "redirect:/store/owner";
        }

        model.addAttribute("store", store);
        model.addAttribute("menu", menuService.getMenuById(menuId));
        // 우리가 만든 계층형 조회 서비스 호출! (그룹 + 하위 옵션들 한 번에 가져옴)
        model.addAttribute("optionGroups", menuOptionService.getMenuOptions(menuId));

        return "menu/option_manage.html"; // 새롭게 만들 HTML
    }

    // 2. 옵션 그룹 추가 (예: 사이즈 선택)
    @PostMapping("/group/add")
    public String addGroup(@PathVariable("storeId") Integer storeId,
                           @PathVariable("menuId") Integer menuId,
                           @ModelAttribute MenuOptionGroup group) {
        group.setMenuId(menuId);
        menuOptionService.addOptionGroup(group);
        return "redirect:/store/" + storeId + "/menu/" + menuId + "/options";
    }

    // 3. 그룹 삭제
    @PostMapping("/group/{groupId}/delete")
    public String deleteGroup(@PathVariable("storeId") Integer storeId,
                              @PathVariable("menuId") Integer menuId,
                              @PathVariable("groupId") Integer groupId) {
        menuOptionService.removeOptionGroup(groupId);
        return "redirect:/store/" + storeId + "/menu/" + menuId + "/options";
    }

    // 4. 상세 옵션 추가 (예: 특 사이즈 +2000원)
    @PostMapping("/group/{groupId}/option/add")
    public String addOption(@PathVariable("storeId") Integer storeId,
                            @PathVariable("menuId") Integer menuId,
                            @PathVariable("groupId") Integer groupId,
                            @ModelAttribute MenuOption option) {
        option.setGroupId(groupId);
        menuOptionService.addOption(option);
        return "redirect:/store/" + storeId + "/menu/" + menuId + "/options";
    }

    // 5. 상세 옵션 삭제
    @PostMapping("/option/{optionId}/delete")
    public String deleteOption(@PathVariable("storeId") Integer storeId,
                               @PathVariable("menuId") Integer menuId,
                               @PathVariable("optionId") Integer optionId) {
        menuOptionService.removeOption(optionId);
        return "redirect:/store/" + storeId + "/menu/" + menuId + "/options";
    }
}
