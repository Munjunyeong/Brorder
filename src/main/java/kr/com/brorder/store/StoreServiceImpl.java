package kr.com.brorder.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    /**내 서비스 안에서만 안전하게 DB에 접근**/
    private StoreDao storeDAO; // DAO 계층 주입

    @Override
    public List<Store> getStoreList(String category, String searchKeyword) {
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("category", category);
        searchMap.put("searchKeyword", searchKeyword);
        // 비즈니스 로직 필요 시 추가 (예: 정렬, 상태 필터링 등)
        return storeDAO.selectStoreList(searchMap);
    }

    @Override
    public Store getStoreById(Integer storeId) {
        return storeDAO.selectStoreById(storeId);
    }

    @Override
    public void addStore(Store store) { storeDAO.insertStore(store); }

    @Override
    public void updateStore(Store store) { storeDAO.updateStore(store); }

    @Override
    public void removeStore(Integer storeId) { storeDAO.deleteStore(storeId); }
}