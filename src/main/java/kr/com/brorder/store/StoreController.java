package kr.com.brorder.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/store") // 주소창에 localhost:9090/store/... 로 접근
public class StoreController {

    @Autowired
    private StoreService storeService;

    // 테스트
    // 1. 판매처 전체 목록 화면 (GET /store/list)
    @GetMapping("/list")
    public String storeList(Model model) {
        List<Store> list = storeService.getStoreList();
        model.addAttribute("stores", list);
        return "store/list"; // src/main/resources/templates/store/list.html
    }

    // 2. 특정 판매처 상세 정보 화면 (GET /store/detail?store_id=1)
    @GetMapping("/detail/{storeId}")
    public String storeDetail(@PathVariable("storeId") Integer store_id, Model model) {
        // 만약 값이 안 넘어왔을 경우에 대한 방어 코드 추가
        if (store_id == null) {
            return "redirect:/store/list"; // 값이 없으면 그냥 리스트 화면으로 튕겨버림
        }

        Store store = storeService.getStoreById(store_id);

        if (store == null) {
            return "error/404";
        }

        model.addAttribute("store", store);
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
    @GetMapping("/update/{storeId}")
    public String updateForm(@PathVariable("storeId") Integer storeId, Model model) {
        model.addAttribute("store", storeService.getStoreById(storeId));
        return "store/update"; // src/main/resources/templates/store/update.html 반환
    }

    // 4. 수정 데이터 처리
    @PostMapping("/update")
    public String updateStore(@ModelAttribute Store store) {
        storeService.updateStore(store);
        return "redirect:/store/detail/" + store.getStoreId();
    }

    // 5. 삭제 처리
    @PostMapping("/delete")
    public String deleteStore(@RequestParam("storeId") Integer storeId) {
        storeService.removeStore(storeId);
        return "redirect:/store/list";
    }
}

