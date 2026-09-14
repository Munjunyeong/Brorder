package kr.com.brorder.menu;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuDaoImpl implements MenuDao {

    @Autowired
    private SqlSession sqlSession;

    private static final String NAMESPACE = "kr.com.brorder.menu.Menu.";

    @Override
    public List<Menu> selectMenuListByStoreId(Integer storeId) {
        return sqlSession.selectList(NAMESPACE + "selectMenuListByStoreId", storeId);
    }

    @Override
    public Menu selectMenuById(Integer menuId) {
        return sqlSession.selectOne(NAMESPACE + "selectMenuById", menuId);
    }

    @Override
    public int insertMenu(Menu menu) {
        return sqlSession.insert(NAMESPACE + "insertMenu", menu);
    }

    @Override
    public int updateMenu(Menu menu) {
        return sqlSession.update(NAMESPACE + "updateMenu", menu);
    }

    @Override
    public int deleteMenu(Integer menuId) {
        return sqlSession.delete(NAMESPACE + "deleteMenu", menuId);
    }
}
