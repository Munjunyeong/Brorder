package kr.com.brorder.review.dao;

import kr.com.brorder.review.domain.MenuOptionDTO;
import kr.com.brorder.review.domain.Review;
import kr.com.brorder.review.domain.ReviewResponseDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    private static final String NAMESPACE = "kr.com.brorder.review.dao.ReviewDao.";

    @Autowired
    SqlSession sqlSession;

    @Override
    public void save(Review review) {
        sqlSession.insert(NAMESPACE + "save", review);
    }

    @Override
    public List<ReviewResponseDTO> findByStoreId(int storeId) {
        return sqlSession.selectList(NAMESPACE + "findByStoreId", storeId);
    }

    @Override
    public List<ReviewResponseDTO> findByUserId(int userId) {
        return sqlSession.selectList(NAMESPACE + "findByUserId", userId);
    }

    @Override
    public Review findById(int reviewId) {
        return sqlSession.selectOne(NAMESPACE + "findById", reviewId);
    }

    @Override
    public void deleteById(int reviewId) {
        sqlSession.delete(NAMESPACE + "deleteById", reviewId);
    }

    @Override
    public void update(Review updateReview) {
        sqlSession.update(NAMESPACE + "update", updateReview);
    }

    // 가게 고유 번호로 해당 매장의 메뉴 옵션 리스트 조회
    @Override
    public List<MenuOptionDTO> findMenusByStoreId(int storeId) {
        return sqlSession.selectList(NAMESPACE + "findMenusByStoreId", storeId);
    }
}
// 06/15 커밋 테스트