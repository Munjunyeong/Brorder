package kr.com.brorder.menu;

import java.util.List;

public interface MenuOptionDao {
    // 그룹 관리
    List<MenuOptionGroup> selectGroupsByMenuId(Integer menuId);
    int insertGroup(MenuOptionGroup group);
    int deleteGroup(Integer groupId);

    // 상세 옵션 관리
    List<MenuOption> selectOptionsByGroupId(Integer groupId);
    int insertOption(MenuOption option);
    int deleteOption(Integer optionId);
}
