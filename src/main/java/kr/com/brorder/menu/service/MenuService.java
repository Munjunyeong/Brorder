package kr.com.brorder.menu.service; //메뉴서비스

import kr.com.brorder.menu.model.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> selectMenuListByStoreId(Integer storeId);
    Menu selectMenuById(Integer menuId);

}