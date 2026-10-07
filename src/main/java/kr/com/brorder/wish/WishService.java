package kr.com.brorder.wish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WishService {

    @Autowired
    private WishMapper wishMapper;

    public boolean toggleWish(Long userid, Integer storeId) {
        if (wishMapper.checkWish(userid, storeId) > 0) {
            wishMapper.deleteWish(userid, storeId);
            return false; // 찜 해제됨
        } else {
            Wish wish = new Wish();
            wish.setUserid(userid);
            wish.setStoreId(storeId);
            wishMapper.insertWish(wish);
            return true; // 찜 등록됨
        }
    }

    public List<Integer> getWishedStoreIds(Long userid) {
        return wishMapper.selectWishedStoreIds(userid);
    }
}
