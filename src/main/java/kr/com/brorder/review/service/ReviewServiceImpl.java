package kr.com.brorder.review.service;

import kr.com.brorder.review.dao.ReviewDao;
import kr.com.brorder.review.domain.MenuOptionDTO;
import kr.com.brorder.review.domain.Review;
import kr.com.brorder.review.domain.ReviewRequestDTO;
import kr.com.brorder.review.domain.ReviewResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 리뷰 핵심 비즈니스 로직 구현 및 데이터 검증 담당 클래스
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    /**
     * 의존성 주입(DI)을 통해 데이터 액세스 객체인 ReviewDao 연결
     */
    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    /**
     * 신규 리뷰 등록 및 저장
     * 화면에서 가공된 리뷰 입력 데이터(DTO)를 엔티티로 변환하고 세션의 작성자 식별 번호를 매핑하여 저장
     */
    @Override
    public void writeReview(ReviewRequestDTO requestDTO, int loginUserId) {
        Review review = new Review();
        review.setStoreId(requestDTO.getStoreId());
        review.setMenuId(requestDTO.getMenuId());
        review.setUserId(loginUserId);
        review.setRating(requestDTO.getRating());
        review.setContent(requestDTO.getContent());
        review.setPicture(requestDTO.getPicture());

        reviewDao.save(review);
    }

    /**
     * 특정 가게의 전체 리뷰 목록 조회
     * 매장 고유 번호(storeId)를 조건으로 매핑된 회원의 이름과 메뉴명이 포함된 리뷰 리스트 조회
     */
    @Override
    public List<ReviewResponseDTO> getStoreReviews(int storeId) {
        return reviewDao.findByStoreId(storeId);
    }

    /**
     * 로그인한 유저 본인의 리뷰 목록 조회
     * 마이페이지 노출용으로 현재 세션 유저 식별 번호(loginUserId)와 일치하는 리뷰 데이터만 필터링하여 조회
     */
    @Override
    public List<ReviewResponseDTO> getMyReviews(int loginUserId) {
        return reviewDao.findByUserId(loginUserId);
    }

    /**
     * 특정 리뷰 삭제 및 권한 검증
     * 리뷰 고유 식별 번호로 글을 파악한 뒤 세션의 유저 번호와 일치하는 본인 글일 경우에만 완전 삭제 수행
     */
    @Override
    public void removeReview(int reviewId, int loginUserId) {
        Review targetReview = reviewDao.findById(reviewId);

        if (targetReview == null) {
            throw new IllegalArgumentException("존재하지 않는 리뷰입니다.");
        }

        if (targetReview.getUserId() != loginUserId) {
            throw new IllegalStateException("본인이 작성한 리뷰만 삭제할 수 있습니다.");
        }

        reviewDao.deleteById(reviewId);
    }

    /**
     * 리뷰 단건 상세 조회
     * 리뷰 수정 화면을 호출할 때 기존에 기입했던 평점, 내용, 이미지 데이터를 폼 태그에 채워주기 위해 단건 조회
     */
    @Override
    public Review getReviewById(int reviewId) {
        return reviewDao.findById(reviewId);
    }

    /**
     * 기존 리뷰 수정 및 권한 검증
     * 데이터 오염이나 주소창 조작을 막기 위해 작성자 본인 여부를 검증하고 최신 평점, 내용, 이미지 파일명을 반영하여 수정
     */
    @Override
    public void modifyReview(int reviewId, ReviewRequestDTO requestDTO, int loginUserId) {
        Review targetReview = reviewDao.findById(reviewId);

        if (targetReview == null) {
            throw new IllegalArgumentException("존재하지 않는 리뷰입니다.");
        }

        if (targetReview.getUserId() != loginUserId) {
            throw new IllegalStateException("본인이 작성한 리뷰만 수정할 수 있습니다.");
        }

        Review updateReview = new Review();
        updateReview.setReviewId(reviewId);
        updateReview.setRating(requestDTO.getRating());
        updateReview.setContent(requestDTO.getContent());
        updateReview.setPicture(requestDTO.getPicture());

        reviewDao.update(updateReview);
    }

    /**
     * 특정 가게의 전체 메뉴 목록 조회
     * 리뷰 작성 폼 등에서 선택 가능한 항목을 보여주기 위해 가게 고유 번호(storeId)에 귀속된 메뉴 옵션 리스트 조회
     */
    @Override
    public List<MenuOptionDTO> getMenusByStoreId(int storeId) {
        return reviewDao.findMenusByStoreId(storeId);
    }
}
// 06/15 커밋 테스트