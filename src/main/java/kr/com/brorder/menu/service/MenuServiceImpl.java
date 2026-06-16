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

    // 메뉴 목록
    @Override
    public List<Menu> selectMenuListByStoreId(Integer storeId) {
        return menuDao.selectMenuListByStoreId(storeId);
    }

    // 단일 메뉴 조회
    @Override
    public Menu selectMenuById(Integer menuId) {
        return menuDao.selectMenuById(menuId);
    }

    // 메뉴 등록
    @Override
    public void insertMenu(Menu menu, MultipartFile file) {

        try {
            if (file != null && !file.isEmpty()) {
                String saveName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                file.transferTo(new File(uploadPath, saveName));
                menu.setImage(saveName);
            }

            menuDao.insertMenu(menu);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 메뉴 삭제
    @Override
    public void deleteMenu(Integer menuId) {

        menuDao.deleteOrderMenuByMenuId(menuId);
        menuDao.deleteMenu(menuId);
    }

    // 메뉴 수정
    @Override
    public void updateMenu(Menu menu, MultipartFile file) {

        Menu origin = menuDao.selectMenuById(menu.getMenuId());
        menu.setStoreId(origin.getStoreId());

        try {
            if (file != null && !file.isEmpty()) {
                String saveName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                file.transferTo(new File(uploadPath, saveName));
                menu.setImage(saveName);
            } else {
                menu.setImage(origin.getImage());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        menuDao.updateMenu(menu);
    }
}