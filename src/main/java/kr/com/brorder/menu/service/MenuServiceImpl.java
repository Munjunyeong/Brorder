package kr.com.brorder.menu.service;

import kr.com.brorder.menu.dao.MenuDao;
import kr.com.brorder.menu.model.Menu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuDao menuDao;

    public MenuServiceImpl(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Value("${kopo.upload.path}")
    private String uploadPath;

    @Override
    public List<Menu> selectMenuListByStoreId(Integer storeId) {
        return menuDao.selectMenuListByStoreId(storeId);
    }

    @Override
    public Menu selectMenuById(Integer menuId) {
        return menuDao.selectMenuById(menuId);
    }

    @Override
    public void insertMenu(Menu menu, MultipartFile file) {

        try {
            // 파일 있을 때만 처리
            if (file != null && !file.isEmpty()) {

                String originalName = file.getOriginalFilename();
                String saveName = System.currentTimeMillis() + "_" + originalName;

                File folder = new File(uploadPath);
                if (!folder.exists()) {
                    folder.mkdirs();
                }

                File saveFile = new File(uploadPath, saveName);
                file.transferTo(saveFile);

                // DB에 저장될 이미지 경로 (/upload/파일명)
                menu.setImage(saveName);
            }

            // DB 저장
            menuDao.insertMenu(menu);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("메뉴 저장 실패");
        }
    }

    @Override
    public void deleteMenu(Integer menuId) {
        menuDao.deleteMenu(menuId);
    }
}