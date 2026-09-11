package kr.com.brorder.menu;

import java.util.List;

public interface MenuDao {
    List<Menu> selectMenuListByStoreId(Integer storeId); // 해당 가게의 모든 메뉴 조회
    Menu selectMenuById(Integer menuId);                 // 메뉴 하나만 상세 조회
    int insertMenu(Menu menu);                           // 메뉴 추가
    int updateMenu(Menu menu);                           // 메뉴 수정
    int deleteMenu(Integer menuId);                      // 메뉴 삭제
}