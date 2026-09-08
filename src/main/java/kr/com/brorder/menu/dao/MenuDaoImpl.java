package kr.com.brorder.menu.dao;//메뉴임플

import kr.com.brorder.menu.model.Menu;
import kr.com.brorder.menu.model.MenuOption;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuDaoImpl implements MenuDao {

    private final SqlSession sqlSession;

    private static final String NAMESPACE =
            "kr.com.brorder.menu.MenuMapper.";

    public MenuDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void insertMenu(Menu menu) {
        sqlSession.insert(NAMESPACE + "insertMenu", menu);
    }

    @Override
    public List<Menu> selectMenuListByStoreId(Integer storeId) {

        return sqlSession.selectList(
                NAMESPACE + "selectMenuListByStoreId",
                storeId);
    }

    @Override
    public Menu selectMenuById(Integer menuId) {
        return sqlSession.selectOne(NAMESPACE + "selectMenuById", menuId);
    }

    @Override
    public List<Menu> list(Integer storeId) {
        return List.of();
    }

    @Override
    public void deleteMenu(Integer menuId) {
        sqlSession.delete(
                NAMESPACE + "deleteMenu",
                menuId);
    }

    @Override
    public void deleteOrderMenuByMenuId(Integer menuId) {
        sqlSession.delete(
                NAMESPACE + "deleteOrderMenuByMenuId",
                menuId
        );
    }

    @Override
    public void updateMenu(Menu menu) {
        sqlSession.update(
                NAMESPACE + "updateMenu",
                menu
        );
    }

    @Override
    public List<MenuOption> selectOptionsByMenuId(Integer menuId) {
        return sqlSession.selectList(NAMESPACE + "selectOptionsByMenuId", menuId);
    }

}