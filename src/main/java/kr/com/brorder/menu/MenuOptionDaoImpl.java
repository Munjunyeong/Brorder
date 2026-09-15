package kr.com.brorder.menu;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class MenuOptionDaoImpl implements MenuOptionDao {

    @Autowired
    private SqlSession sqlSession;
    private static final String NAMESPACE = "kr.com.brorder.menu.MenuOption.";

    @Override
    public List<MenuOptionGroup> selectGroupsByMenuId(Integer menuId) {
        return sqlSession.selectList(NAMESPACE + "selectGroupsByMenuId", menuId);
    }
    @Override
    public int insertGroup(MenuOptionGroup group) { return sqlSession.insert(NAMESPACE + "insertGroup", group); }
    @Override
    public int deleteGroup(Integer groupId) { return sqlSession.delete(NAMESPACE + "deleteGroup", groupId); }

    @Override
    public List<MenuOption> selectOptionsByGroupId(Integer groupId) {
        return sqlSession.selectList(NAMESPACE + "selectOptionsByGroupId", groupId);
    }
    @Override
    public int insertOption(MenuOption option) { return sqlSession.insert(NAMESPACE + "insertOption", option); }
    @Override
    public int deleteOption(Integer optionId) { return sqlSession.delete(NAMESPACE + "deleteOption", optionId); }
}
