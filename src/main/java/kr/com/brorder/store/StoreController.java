package kr.com.brorder.store;

import kr.com.brorder.menu.model.Menu;
import kr.com.brorder.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/store") // 주소창에 localhost:9090/store/... 로 접근
public class StoreController {

    @Autowired
    /**내 서비스 안에서만 안전하게 DB에 접근**/
    private StoreService storeService;

    @Autowired
    private MenuService menuService;

    // 테스트
    // 1. 판매처 목록 조회 (카테고리 필터 + 이름 검색창) (GET /store/list)
    @GetMapping("/list")
    public String storeList(@RequestParam(value = "category", required = false) String category,
                            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                            Model model) {
        // 카테고리와 검색어를 모두 서비스로 전달
        List<Store> list = storeService.getStoreList(category, searchKeyword);

        model.addAttribute("stores", list);
        model.addAttribute("selectedCategory", category); // 선택된 카테고리 강조용
        model.addAttribute("searchKeyword", searchKeyword); // 검색 창에 입력한 단어 유지용

        return "store/list"; // src/main/resources/templates/store/list.html
    }

    // 2. 특정 판매처 상세 정보 화면 (GET /store/{storeId})
    @GetMapping("/{storeId}")
    public String storeDetail(@PathVariable("storeId") Integer store_id, Model model) {

        // 만약 값이 안 넘어왔을 경우에 대한 방어 코드 추가
        if (store_id == null) {
            return "redirect:/store/list"; // 값이 없으면 그냥 리스트 화면으로 튕겨버림
        }

        Store store = storeService.getStoreById(store_id);

        if (store == null) {
            return "error/404";
        }

        List<Menu> menuList = menuService.selectMenuListByStoreId(store_id);

        model.addAttribute("store", store);
        model.addAttribute("menuList", menuList);

        return "store/detail"; // src/main/resources/templates/store/detail.html
    }

    // 1. 등록 화면 이동
    @GetMapping("/add")
    public String addForm() {
        return "store/add"; // src/main/resources/templates/store/add.html 반환
    }

    // 2. 등록 데이터 처리
    @PostMapping("/add")
    public String addStore(@ModelAttribute Store store) {
        storeService.addStore(store);
        return "redirect:/store/list";
    }

    // 3. 수정 화면 이동
    @GetMapping("/{storeId}/update")
    public String updateForm(@PathVariable("storeId") Integer storeId, Model model) {
        model.addAttribute("store", storeService.getStoreById(storeId));
        return "store/update"; // src/main/resources/templates/store/update.html 반환
    }

    // 4. 수정 데이터 처리
    @PostMapping("{storeId}/update")
    public String updateStore(@PathVariable("storeId") Integer  storeId, @ModelAttribute Store store) {
        store.setStoreId(storeId);

        storeService.updateStore(store);
        return "redirect:/store/" + store.getStoreId();
    }

    // 5. 삭제 처리
    @PostMapping("{storeId}/delete")
    public String deleteStore(@PathVariable("storeId") Integer storeId) {
        storeService.removeStore(storeId);
        return "redirect:/store/list";
    }
}

