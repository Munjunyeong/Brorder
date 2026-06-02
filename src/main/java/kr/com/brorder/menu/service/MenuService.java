package kr.com.brorder.menu.service;

import kr.com.brorder.menu.model.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> selectMenuListByStoreId(Integer storeId);

}