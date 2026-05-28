package kr.com.brorder.store;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class StoreDaoImpl implements StoreDao {

    @Autowired
    SqlSession sqlSession; // MyBatis의 핵심 객체 주입

    // XML 파일에 지정할 namespace 경로 상수로 지정
    private static final String NAMESPACE = "kr.com.brorder.store.Store.";

    @Override
    public List<Store> selectStoreList(Map<String, Object> searchMap) {
        // XML의 <select id="selectStoreList"> 호출
        return sqlSession.selectList(NAMESPACE + "selectStoreList", searchMap);
    }

    @Override
    public Store selectStoreById(Integer store_id) {
        // XML의 <select id="selectStoreById"> 호출
        return sqlSession.selectOne(NAMESPACE + "selectStoreById", store_id);
    }

    @Override
    public int insertStore(Store store) { return sqlSession.insert(NAMESPACE + "insertStore", store); }

    @Override
    public int updateStore(Store store) { return sqlSession.update(NAMESPACE + "updateStore", store); }

    @Override
    public int deleteStore(Integer storeId) { return sqlSession.delete(NAMESPACE + "deleteStore", storeId); }
}
