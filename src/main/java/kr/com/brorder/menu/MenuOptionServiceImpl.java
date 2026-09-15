package kr.com.brorder.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuOptionServiceImpl implements MenuOptionService {

    @Autowired
    private MenuOptionDao menuOptionDao;

    @Override
    public List<MenuOptionGroup> getMenuOptions(Integer menuId) {
        // 1. 해당 메뉴의 전체 옵션 그룹을 가져옵니다.
        List<MenuOptionGroup> groups = menuOptionDao.selectGroupsByMenuId(menuId);

        // 2. 각 그룹마다 반복문을 돌며 하위 상세 옵션들을 조회해 리스트에 담아줍니다.
        for (MenuOptionGroup group : groups) {
            List<MenuOption> options = menuOptionDao.selectOptionsByGroupId(group.getGroupId());
            group.setOptions(options); // DTO에 만들어둔 필드에 주입
        }

        return groups;
    }

    @Override
    public void addOptionGroup(MenuOptionGroup group) { menuOptionDao.insertGroup(group); }
    @Override
    public void removeOptionGroup(Integer groupId) { menuOptionDao.deleteGroup(groupId); }
    @Override
    public void addOption(MenuOption option) { menuOptionDao.insertOption(option); }
    @Override
    public void removeOption(Integer optionId) { menuOptionDao.deleteOption(optionId); }
}
