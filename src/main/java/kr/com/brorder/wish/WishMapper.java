package kr.com.brorder.wish;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface WishMapper {
    int insertWish(Wish wish);
    int deleteWish(@Param("userid") Long userid, @Param("storeId") Integer storeId);
    int checkWish(@Param("userid") Long userid, @Param("storeId") Integer storeId);
    List<Integer> selectWishedStoreIds(Long userid);
}
