package kr.com.brorder.menu.dao;

import kr.com.brorder.menu.model.Menu;

import java.util.List;

public interface MenuDao {

    List<Menu> selectMenuListByStoreId(Integer storeId);

    List<Menu> list(Integer storeId);
}
