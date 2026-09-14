package kr.com.brorder.menu.service;

import java.util.List;
import kr.com.brorder.menu.model.Menu;

public interface MenuService {
    List<Menu> getMenusByStoreId(Integer storeId);
    Menu getMenuById(Integer menuId);
    void addMenu(Menu menu);
    void updateMenu(Menu menu);
    void removeMenu(Integer menuId);
}
