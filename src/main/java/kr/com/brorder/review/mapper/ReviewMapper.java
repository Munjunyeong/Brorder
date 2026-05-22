package kr.com.brorder.review.mapper;

import kr.com.brorder.review.domain.Review;
import kr.com.brorder.review.domain.ReviewResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper // [역할] 스프링 부트에게 이 인터페이스가 MyBatis 매퍼 인터페이스임을 알려줍니다.
public interface ReviewMapper {

    /**
     * [역할] 새로운 리뷰 데이터를 DB에 저장합니다.
     * [정보 이동] Service 레이어에서 완성된 Review 객체를 받아 review 테이블에 INSERT 쿼리를 실행합니다.
     */
    void save(Review review);

    /**
     * [역할] 특정 가게에 등록된 모든 리뷰 목록을 가져옵니다.
     * [정보 이동] Controller가 넘겨준 storeId를 조건(WHERE)으로 받아 DB에서 조회합니다.
     * [특징] users, menu 테이블과 JOIN된 결과물인 ReviewResponseDTO 리스트를 Service 레이어로 반환합니다.
     */
    List<ReviewResponseDTO> findByStoreId(int storeId);

    /**
     * [역할] 로그인한 특정 회원이 작성한 본인의 리뷰 목록만 가져옵니다.
     * [정보 이동] 세션 등에서 추출된 userId를 조건으로 받아 DB에서 조회합니다.
     * [특징] 어떤 가게에 썼는지 보여주기 위해 store 테이블과 JOIN된 결과 리스트를 반환합니다.
     */
    List<ReviewResponseDTO> findByUserId(int userId);

    /**
     * [역할] 특정 리뷰를 고유 번호(ID) 기준으로 조회합니다. (삭제 전 권한 체크용)
     * [정보 이동] reviewId를 받아 해당 리뷰의 모든 데이터를 테이블에서 가져와 Service 레이어로 넘깁니다.
     */
    Review findById(int reviewId);

    /**
     * [역할] 등록된 리뷰를 데이터베이스에서 삭제합니다.
     * [정보 이동] 검증이 끝난 reviewId를 받아 review 테이블에서 해당 행을 DELETE 쿼리로 지웁니다.
     */
    void deleteById(int reviewId);

    // ReviewMapper.java 파일 가장 하단에 추가
    void update(Review review);
}