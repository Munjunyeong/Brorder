package kr.com.brorder.menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenusByStoreId(Integer storeId);
    Menu getMenuById(Integer menuId);
    void addMenu(Menu menu);
    void updateMenu(Menu menu);
    void removeMenu(Integer menuId);
}
