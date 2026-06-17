package kr.com.brorder.store;

import java.util.List;
import java.util.Map;

public interface StoreDao {
    List<Store> selectStoreList(Map<String, Object> searchMap);
    List<Store> selectStoreListByOwner(Long userId);
    Store selectStoreById(Integer store_id);

    int insertStore(Store store);
    int updateStore(Store store);
    int deleteStore(Integer storeId);
}
