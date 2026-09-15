package kr.com.brorder.menu;

import java.util.List;

public class MenuOptionGroup {
    private Integer groupId;    // PK
    private Integer menuId;     // FK: 소속된 메뉴 ID
    private String name;        // 그룹명 (예: 사이즈 선택, 곁들임 메뉴)

    // 💡 화면 출력용: 이 그룹에 속한 상세 옵션(사이즈업, 맛보기 순대 등)들을 한 번에 담기 위한 리스트
    private List<MenuOption> options;

    public MenuOptionGroup() {}

    public Integer getGroupId() { return groupId; }
    public void setGroupId(Integer groupId) { this.groupId = groupId; }
    public Integer getMenuId() { return menuId; }
    public void setMenuId(Integer menuId) { this.menuId = menuId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<MenuOption> getOptions() { return options; }
    public void setOptions(List<MenuOption> options) { this.options = options; }
}
