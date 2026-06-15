package kr.com.brorder.menu.service;

import kr.com.brorder.menu.model.Menu;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MenuService {

    List<Menu> selectMenuListByStoreId(Integer storeId);

    Menu selectMenuById(Integer menuId);

    void insertMenu(Menu menu, MultipartFile file) throws IOException;
}