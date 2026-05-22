package kr.com.brorder.review.service;

import kr.com.brorder.review.domain.ReviewRequestDTO;
import kr.com.brorder.review.domain.ReviewResponseDTO;
import java.util.List;

public interface ReviewService {

    /**
     * [역할] 사용자가 작성한 리뷰 내용을 받아 검증 및 변환 후 저장합니다.
     * [정보 이동] Controller가 전달한 'ReviewRequestDTO'와 세션의 'loginUserId'를 받아 로직을 처리합니다.
     */
    void writeReview(ReviewRequestDTO requestDTO, int loginUserId);

    /**
     * [역할] 선택한 가게의 모든 리뷰 목록을 가져옵니다.
     * [정보 이동] Controller로부터 storeId를 넘겨받아 Mapper로 전달하고, 가공된 목록을 다시 Controller로 넘깁니다.
     */
    List<ReviewResponseDTO> getStoreReviews(int storeId);

    /**
     * [역할] 내가 작성한 리뷰 목록만 모아서 가져옵니다.
     * [정보 이동] 세션에서 추출된 loginUserId를 기반으로 본인의 리뷰만 조회하여 반환합니다.
     */
    List<ReviewResponseDTO> getMyReviews(int loginUserId);

    /**
     * [역할] 지정한 리뷰를 삭제합니다. (본인 확인 로직 포함)
     * [정보 이동] 삭제할 reviewId와 안전한 검증을 위해 현재 로그인한 사용자의 loginUserId를 함께 전달받습니다.
     */
    void removeReview(int reviewId, int loginUserId);

    /* ========================================================================
     * [종민님 요청사항 반영] 여기서부터 수정 기능을 지원하기 위해 추가된 비즈니스 명세입니다.
     * ======================================================================== */

    /**
     * [역할] 수정 폼에 기존 작성 내용을 먼저 채워주기 위해 리뷰 번호 단건으로 전체 데이터를 조회합니다.
     * [정보 이동] Controller로부터 reviewId를 넘겨받아 도메인 객체로 반환합니다.
     */
    kr.com.brorder.review.domain.Review getReviewById(int reviewId);

    /**
     * [역할] 사용자가 수정한 새로운 내용을 받아 검증한 뒤 데이터베이스를 최신화(UPDATE)합니다.
     * [정보 이동] 수정할 대상 reviewId, 새로 입력된 데이터가 담긴 requestDTO, 권한 체크용 loginUserId를 함께 전달받습니다.
     */
    void modifyReview(int reviewId, ReviewRequestDTO requestDTO, int loginUserId);
}