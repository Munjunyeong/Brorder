package kr.com.brorder.review.dao;

import kr.com.brorder.review.domain.MenuOptionDTO;
import kr.com.brorder.review.domain.Review;
import kr.com.brorder.review.domain.ReviewResponseDTO;

import java.util.List;

public interface ReviewDao {

    void save(Review review);

    List<ReviewResponseDTO> findByStoreId(int storeId);

    List<ReviewResponseDTO> findByUserId(int userId);

    Review findById(int reviewId);

    void deleteById(int reviewId);

    void update(Review updateReview);

    List<MenuOptionDTO> findMenusByStoreId(int storeId);
}