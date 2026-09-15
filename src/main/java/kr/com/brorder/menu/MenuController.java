package kr.com.brorder.menu;

import kr.com.brorder.store.Store;
import kr.com.brorder.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * [메뉴 컨트롤러]
 * 사장님이 특정 가게의 메뉴를 관리(조회/등록/수정/삭제)하는 요청을 처리합니다.
 * 공통 경로로 /store/{storeId}/menu를 설정하여 URL 구조를 직관적으로 만들었습니다.
 */
@Controller
@RequestMapping("/store/{storeId}/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // 화면에 가게 이름(가게 정보)을 띄워주기 위해 StoreService도 함께 주입받습니다.
    @Autowired
    private StoreService storeService;

    @Value("${kopo.upload.path}")
    private String path;

    // 1. 사장님 전용 메뉴 관리 대시보드 화면
    @GetMapping("/manage")
    public String manageMenu(@PathVariable("storeId") Integer storeId, Model model) {
        Store store = storeService.getStoreById(storeId);
        List<Menu> menuList = menuService.getMenusByStoreId(storeId);

        model.addAttribute("store", store);
        model.addAttribute("menuList", menuList);

        return "menu/manage"; // src/main/resources/templates/menu/manage.html 반환
    }

    // 2. 새 메뉴 등록 폼 화면 이동
    @GetMapping("/add")
    public String addMenuForm(@PathVariable("storeId") Integer storeId, Model model) {
        Store store = storeService.getStoreById(storeId);
        model.addAttribute("store", store); // "어느 가게에 등록 중인지" 뷰에 알려줌

        return "menu/add";
    }

    // 3. 새 메뉴 데이터 저장 처리
    @PostMapping("/add")
    public String addMenu(@PathVariable("storeId") Integer storeId,
                          @ModelAttribute Menu menu,
                          @RequestParam("imageFile") MultipartFile imageFile) {

        // 💡 중요: 폼에서 전달받은 메뉴 객체에 소속된 가게의 ID(외래키)를 반드시 강제로 세팅해 줍니다.
        menu.setStoreId(storeId);

        // 이미지 파일이 정상적으로 업로드된 경우에만 저장 처리
        if (imageFile != null && !imageFile.isEmpty()) {
            String savedFileName = saveImage(imageFile);
            menu.setPicture(savedFileName);
        }

        menuService.addMenu(menu);

        // 저장이 끝나면 해당 가게의 메뉴 관리 목록 화면으로 리다이렉트
        return "redirect:/store/" + storeId + "/menu/manage";
    }

    // 4. 메뉴 삭제 처리
    @PostMapping("/{menuId}/delete")
    public String deleteMenu(@PathVariable("storeId") Integer storeId,
                             @PathVariable("menuId") Integer menuId) {
        menuService.removeMenu(menuId);

        // 삭제 완료 후 메뉴 관리 목록 화면으로 리다이렉트
        return "redirect:/store/" + storeId + "/menu/manage";
    }

    // [유틸리티] 파일 저장 메서드 (이전에 리뷰했던 안전한 경로 병합 방식 적용)
    private String saveImage(MultipartFile file) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalName = file.getOriginalFilename();
        String ext = originalName.substring(originalName.lastIndexOf("."));
        String savedFileName = UUID.randomUUID().toString() + ext;

        try {
            // Paths.get()을 사용해 OS 환경에 맞춰 디렉토리 경로와 파일명을 안전하게 합쳐줍니다.
            Path savePath = Paths.get(path, savedFileName);
            file.transferTo(savePath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedFileName;
    }
}
