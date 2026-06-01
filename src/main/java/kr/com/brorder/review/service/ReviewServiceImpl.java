package kr.com.brorder.review.service;

import kr.com.brorder.review.dao.ReviewDao; // [수정점] 새롭게 생성한 ReviewDao 임포트
import kr.com.brorder.review.domain.Review;
import kr.com.brorder.review.domain.ReviewRequestDTO;
import kr.com.brorder.review.domain.ReviewResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * [리뷰 도메인 서비스 구현체]
 * 리뷰 관련 핵심 비즈니스 로직을 실제로 수행하며 데이터의 가공 및 보안 검증을 담당하는 클래스임
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    // [수정점] 기존 ReviewMapper reviewMapper를 ReviewDao로 전면 교체함
    private final ReviewDao reviewDao;

    /**
     * 생성자 주입을 통해 데이터베이스 제어를 담당하는 ReviewDao 빈(Bean)을 주입받음
     */
    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    /**
     * [신규 리뷰 등록 로직]
     * 화면에서 입력된 DTO 데이터를 DB 테이블 구조에 맞는 순수 엔티티(Review) 객체로 변환하여 저장함
     * 이 과정에서 변조 불가능한 서버 세션의 사용자 번호를 작성자 정보로 안전하게 세팅함
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

        // [수정점] 매퍼 호출에서 DAO 호출로 변경됨
        reviewDao.save(review);
    }

    /**
     * [특정 가게의 리뷰 목록 조회]
     * 매장 고유 번호를 매퍼로 넘겨 연동된 조인(JOIN) 결과 리스트를 가져와 컨트롤러로 전달함
     */
    @Override
    public List<ReviewResponseDTO> getStoreReviews(int storeId) {
        // [수정점] 매퍼 호출에서 DAO 호출로 변경됨
        return reviewDao.findByStoreId(storeId);
    }

    /**
     * [로그인한 유저 본인의 리뷰 목록 조회]
     * 세션에서 인증 완료된 유저 고유 식별 번호를 기반으로 본인이 작성한 리뷰 내역만 필터링하여 가져옴
     */
    @Override
    public List<ReviewResponseDTO> getMyReviews(int loginUserId) {
        // [수정점] 매퍼 호출에서 DAO 호출로 변경됨
        return reviewDao.findByUserId(loginUserId);
    }

    /**
     * [리뷰 삭제 처리 및 권한 검증]
     * 삭제 대상 글의 존재 여부를 먼저 영속성 확인용으로 조회함
     * 글을 작성한 회원의 식별 번호와 현재 삭제 요청을 보낸 유저의 세션 번호가 다를 경우 예외를 발생시켜 차단함
     */
    @Override
    public void removeReview(int reviewId, int loginUserId) {
        // [수정점] 매퍼 호출에서 DAO 호출로 변경됨
        Review targetReview = reviewDao.findById(reviewId);

        if (targetReview == null) {
            throw new IllegalArgumentException("존재하지 않는 리뷰입니다.");
        }

        if (targetReview.getUserId() != loginUserId) {
            throw new IllegalStateException("본인이 작성한 리뷰만 삭제할 수 있습니다.");
        }

        // [수정점] 매퍼 호출에서 DAO 호출로 변경됨
        reviewDao.deleteById(reviewId);
    }

    /**
     * [리뷰 단건 상세 조회]
     * 수정 폼 양식 태그 내부에 기존 텍스트와 평점 데이터를 기본값으로 채워주기 위해 단건 데이터를 찾아옴
     */
    @Override
    public Review getReviewById(int reviewId) {
        // [수정점] 매퍼 호출에서 DAO 호출로 변경됨
        return reviewDao.findById(reviewId);
    }

    /**
     * [기존 리뷰 수정 처리 및 권한 검증]
     * 주소창 조작을 통한 비정상적 접근을 원천 차단하기 위해 수정 전 영속성 조회와 작성자 ID 일치 여부를 철저하게 검증함
     * 검증 통과 시 새로 바뀐 평점, 글 내용, 첨부 이미지 명칭을 갱신용 객체에 담아 데이터베이스를 최신화함
     */
    @Override
    public void modifyReview(int reviewId, ReviewRequestDTO requestDTO, int loginUserId) {
        // [수정점] 매퍼 호출에서 DAO 호출로 변경됨
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

        // [수정점] 매퍼 호출에서 DAO 호출로 변경됨
        reviewDao.update(updateReview);
    }
}