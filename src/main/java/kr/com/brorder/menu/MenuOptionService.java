package kr.com.brorder.menu;

import java.util.List;

public interface MenuOptionService {
    // 특정 메뉴의 모든 옵션 그룹과 그 안의 상세 옵션들을 계층 구조로 묶어서 반환
    List<MenuOptionGroup> getMenuOptions(Integer menuId);

    void addOptionGroup(MenuOptionGroup group);
    void removeOptionGroup(Integer groupId);
    void addOption(MenuOption option);
    void removeOption(Integer optionId);
}
