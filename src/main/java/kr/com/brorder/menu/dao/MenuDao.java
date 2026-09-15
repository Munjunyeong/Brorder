package kr.com.brorder.menu.dao; //메뉴다오

import kr.com.brorder.menu.model.Menu;
import kr.com.brorder.menu.model.MenuOption;

import java.util.List;

public interface MenuDao {

    void insertMenu(Menu menu);
    List<Menu> selectMenuListByStoreId(Integer storeId);
    Menu selectMenuById(Integer menuId);
    List<Menu> list(Integer storeId);

    void deleteMenu(Integer menuId);

    void deleteOrderMenuByMenuId(Integer menuId);

    void updateMenu(Menu menu);

    List<MenuOption> selectOptionsByMenuId(Integer menuId);
}