package kr.com.brorder.review.mapper;

import kr.com.brorder.review.domain.Review;
import kr.com.brorder.review.domain.ReviewResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * [리뷰 데이터베이스 매퍼 인터페이스]
 * 마이바티스(MyBatis) 프레임워크와 연동되어 XML 파일에 작성된 SQL 쿼리문을 호출하는 인터페이스임
 */
@Mapper
public interface ReviewMapper {

    /**
     * [신규 리뷰 데이터 저장]
     * 전달받은 Review 엔티티 객체의 정보를 바탕으로 데이터베이스 review 테이블에 INSERT 쿼리를 실행함
     */
    void save(Review review);

    /**
     * [가게 고유 번호 기준 리뷰 목록 조회]
     * 특정 가게(storeId)의 리뷰들을 조회하며, 작성자 정보 및 메뉴명을 함께 출력하기 위해 users, menu 테이블과 JOIN된 결과를 반환함
     */
    List<ReviewResponseDTO> findByStoreId(int storeId);

    /**
     * [사용자 고유 번호 기준 리뷰 목록 조회]
     * 마이페이지 등에서 로그인한 특정 회원(userId)이 작성한 본인의 전체 리뷰 내역을 JOIN 쿼리로 조회하여 반환함
     */
    List<ReviewResponseDTO> findByUserId(int userId);

    /**
     * [리뷰 단건 조회]
     * 리뷰 고유 식별 번호(reviewId)를 조건으로 해당 리뷰의 원본 상세 데이터를 데이터베이스에서 찾아옴
     */
    Review findById(int reviewId);

    /**
     * [리뷰 데이터 삭제]
     * 검증이 완료된 특정 리뷰(reviewId)의 데이터를 데이터베이스 review 테이블에서 완전 삭제(DELETE)함
     */
    void deleteById(int reviewId);

    /**
     * [리뷰 데이터 수정 반영]
     * 사용자가 새로 수정한 평점, 글 내용, 이미지 정보를 바탕으로 기존 데이터베이스 레코드를 업데이트(UPDATE)함
     */
    void update(Review updateReview);
}