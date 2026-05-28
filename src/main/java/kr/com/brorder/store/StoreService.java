package kr.com.brorder.store;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StoreService {

    // 전체 판매처 목록 가져오기 명세
    List<Store> getStoreList(String category, String searchKeyword);

    // ID로 특정 판매처 하나만 가져오기 명세
    Store getStoreById(Integer store_id);

    void addStore(Store store);
    void updateStore(Store store);
    void removeStore(Integer storeId);
}