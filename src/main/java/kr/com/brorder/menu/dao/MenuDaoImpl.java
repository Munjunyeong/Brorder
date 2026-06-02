package kr.com.brorder.menu.dao;

import kr.com.brorder.menu.model.Menu;
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
    public List<Menu> selectMenuListByStoreId(Integer storeId) {

        return sqlSession.selectList(
                NAMESPACE + "selectMenuListByStoreId",
                storeId);
    }

    @Override
    public List<Menu> list(Integer storeId) {
        return List.of();
    }
}