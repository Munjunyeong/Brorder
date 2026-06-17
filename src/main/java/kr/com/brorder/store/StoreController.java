package kr.com.brorder.store;

import jakarta.servlet.http.HttpSession;
import kr.com.brorder.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/store") // 주소창에 localhost:9090/store/... 로 접근
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Value("${kopo.upload.path}")
    private String path;

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
    public String addStore(@ModelAttribute Store store,
                           @RequestParam("imageFile") MultipartFile imageFile,
                            HttpSession session) {
        // 세션에서 현재 로그인한 사장님 정보 꺼내기
        Users users = (Users) session.getAttribute("users");
        if (users != null) {
            store.setUserid(users.getUserid()); // 가게 객체에 사장님 고유 ID 세팅
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            String savedFileName = saveImage(imageFile);
            store.setTitleImage(savedFileName);
        }

        storeService.addStore(store);
//        return "redirect:/store/list";
        return "redirect:/store/owner";
    }

    // 3. 수정 화면 이동
    @GetMapping("/{storeId}/update")
    public String updateForm(@PathVariable("storeId") Integer storeId, Model model) {
        model.addAttribute("store", storeService.getStoreById(storeId));
        return "store/update"; // src/main/resources/templates/store/update.html 반환
    }

    // 4. 수정 데이터 처리
    @PostMapping("{storeId}/update")
    public String updateStore(@PathVariable("storeId") Integer  storeId,
                              @ModelAttribute Store store,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              HttpSession session) {
        store.setStoreId(storeId);

        if (imageFile != null && !imageFile.isEmpty()) {
            String savedFilename = saveImage(imageFile);
            store.setTitleImage(savedFilename);
        }

        storeService.updateStore(store);
        return "redirect:/store/" + store.getStoreId();
    }

    // 5. 삭제 처리
    @PostMapping("{storeId}/delete")
    public String deleteStore(@PathVariable("storeId") Integer storeId) {
        storeService.removeStore(storeId);
        return "redirect:/store/list";
    }
    // 이미지 저장 유틸리티 메서드 내 변수명 수정
    private String saveImage(MultipartFile file) {
        // 주입받은 path 변수를 사용하여 파일 객체를 생성합니다.
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalName = file.getOriginalFilename();
        String ext = originalName.substring(originalName.lastIndexOf("."));
        String savedFileName = UUID.randomUUID().toString() + ext;

        try {
            // 주입받은 path 경로 뒤에 파일명을 붙여 저장합니다
            file.transferTo(new File(path + savedFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedFileName;
    }
//판매자(사장님) 메소드
    @GetMapping("/owner")
    public String ownerStoreList(HttpSession session, Model model) {
        // 세션에서 로그인한 유저 정보 추출
        Users users = (Users) session.getAttribute("users");

//        // 만약 세션이 없거나 사장님/관리자가 아니라면 접근 차단 (인터셉터가 막아주겠지만 이중 방어)
//        if (users == null || (!"OWNER".equals(users.getRole()) && !"ADMIN".equals(users.getRole()))) {
//            return "redirect:/";
//        }

        // 로그인한 사장님의 고유 번호(userid)로 등록된 가게만 조회
        List<Store> myStores = storeService.getStoreListByOwner(users.getUserid());

        model.addAttribute("stores", myStores);
        return "store/owner_list"; // 💡 src/main/resources/templates/store/owner_list.html
    }
}

