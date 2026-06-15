package kr.com.brorder.review.dao;

import kr.com.brorder.review.domain.MenuOptionDTO;
import kr.com.brorder.review.domain.Review;
import kr.com.brorder.review.domain.ReviewResponseDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 리뷰 DAO 인터페이스
 * 서비스에서 받은 데이터를 Mapper XML로 전달하는 중간 다리 역할
 */
public interface ReviewDao {

    /**
     * 서비스 → DAO → Mapper
     * 신규 리뷰 엔티티를 DB insert로 넘김
     */
    void save(Review review);

    /**
     * 서비스 → DAO → Mapper
     * 특정 가게 번호(storeId)를 넘겨 해당 가게 리뷰 전체 조회
     */
    List<ReviewResponseDTO> findByStoreId(
            @Param("storeId") int storeId
    );

    /**
     * 서비스 → DAO → Mapper
     * 로그인한 회원 번호(userId)를 넘겨 본인이 작성한 리뷰만 조회
     *
     * XML:
     * WHERE r.user_id = #{userId}
     */
    List<ReviewResponseDTO> findByUserId(
            @Param("userId") int userId
    );

    /**
     * 서비스 → DAO → Mapper
     * 리뷰 번호(reviewId)를 넘겨 단건 상세 조회
     */
    Review findById(
            @Param("reviewId") int reviewId
    );

    /**
     * 서비스 → DAO → Mapper
     * 리뷰 번호(reviewId)를 넘겨 삭제 처리
     */
    void deleteById(
            @Param("reviewId") int reviewId
    );

    /**
     * 서비스 → DAO → Mapper
     * 수정된 리뷰 엔티티 전체를 넘겨 update 처리
     */
    void update(Review updateReview);

    /**
     * 서비스 → DAO → Mapper
     * 가게 번호(storeId)를 넘겨 해당 매장의 메뉴 리스트 조회
     */
    List<MenuOptionDTO> findMenusByStoreId(
            @Param("storeId") int storeId
    );
}