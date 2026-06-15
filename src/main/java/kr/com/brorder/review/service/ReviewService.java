package kr.com.brorder.review.service;

import kr.com.brorder.review.domain.MenuOptionDTO;
import kr.com.brorder.review.domain.Review;
import kr.com.brorder.review.domain.ReviewRequestDTO;
import kr.com.brorder.review.domain.ReviewResponseDTO;
import java.util.List;

/**
 * [리뷰 도메인 서비스 인터페이스]
 * 리뷰 관련 핵심 비즈니스 로직의 명세를 정의해 둔 레이어임
 */
public interface ReviewService {

    /**
     * [신규 리뷰 등록 및 저장]
     * 화면에서 넘어온 작성 데이터(DTO)와 세션의 로그인 유저 번호를 받아 데이터베이스에 저장할 엔티티로 가공 후 등록함
     */
    void writeReview(ReviewRequestDTO requestDTO, int loginUserId);

    /**
     * [특정 가게의 전체 리뷰 목록 조회]
     * 지정된 가게 번호(storeId)를 기반으로 조인(JOIN) 쿼리가 반영된 리뷰 리스트를 가져와 반환함
     */
    List<ReviewResponseDTO> getStoreReviews(int storeId);

    /**
     * [내가 작성한 전체 리뷰 목록 조회]
     * 안전하게 세션에서 인증된 유저 번(loginUserId)를 조건으로 본인이 작성한 리뷰 목록만 필터링하여 가져옴
     */
    List<ReviewResponseDTO> getMyReviews(int loginUserId);

    /**
     * [특정 리뷰 삭제 처리]
     * 요청된 리뷰 번호(reviewId)와 현재 로그인한 사용자의 번호(loginUserId)를 비교 검증하여 본인 글인 경우에만 완전 삭제를 수행함
     */
    void removeReview(int reviewId, int loginUserId);

    /**
     * [리뷰 단건 상세 조회]
     * 리뷰 수정 화면 등을 열 때 기존에 작성했던 평점, 내용 등의 데이터를 그대로 채워주기 위해 식별자 번호로 데이터를 찾아옴
     */
    Review getReviewById(int reviewId);

    /**
     * [기존 리뷰 데이터 수정 반영]
     * 사용자가 새로 수정한 데이터와 수정 권한(본인 여부)을 검증한 뒤 데이터베이스 레코드를 최신 정보로 변경함
     */
    void modifyReview(int reviewId, ReviewRequestDTO requestDTO, int loginUserId);

    /**
     * [특정 가게의 전체 메뉴 목록 조회]
     * 지정된 가게 번호(storeId)를 기반으로 해당 매장에 등록된 메뉴 옵션 리스트 조회
     */
    List<MenuOptionDTO> getMenusByStoreId(int storeId);
}
// 06/15 커밋 테스트