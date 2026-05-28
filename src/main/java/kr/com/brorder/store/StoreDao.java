package kr.com.brorder.store;

import java.util.List;

public interface StoreDao {
    List<Store> selectStoreList(String category);
    Store selectStoreById(Integer store_id);

    int insertStore(Store store);
    int updateStore(Store store);
    int deleteStore(Integer storeId);
}
