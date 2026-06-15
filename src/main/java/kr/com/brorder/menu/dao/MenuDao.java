package kr.com.brorder.menu.dao; //메뉴다오

import kr.com.brorder.menu.model.Menu;

import java.util.List;

public interface MenuDao {

    List<Menu> selectMenuListByStoreId(Integer storeId);
    Menu selectMenuById(Integer menuId);
    List<Menu> list(Integer storeId);
}
