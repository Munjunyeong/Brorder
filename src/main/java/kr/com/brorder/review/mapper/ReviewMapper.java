package kr.com.brorder.review.mapper;

import kr.com.brorder.review.domain.Review;
import kr.com.brorder.review.domain.ReviewResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * [마이바티스 영속성 레이어 매퍼 연결체]
 * 서비스 레이어로부터 전달받은 자바 매개변수 데이터를 ReviewMapper.xml에 선언된 SQL ID와 1:1 동기화하여 데이터베이스 질의를 수행하고 결과를 DTO/엔티티 객체로 조립하여 반환하는 컴포넌트
 */
@Mapper
public interface ReviewMapper {

    /**
     * [신규 리뷰 적재 및 시퀀스 연동]
     * 컨트롤러에서 캡처된 가공 엔티티(Review)를 인자로 받아 MyBatis INSERT문을 가동하고, 파라미터 내부에 세팅된 store_id, user_id, menu_id 및 텍스트 본문과 별점 점수를 DB review 테이블의 신규 레코드로 영구 삽입 연산하는 기능
     */
    void save(Review review);

    /**
     * [점포 식별자 매핑 다중 행 조인 조회]
     * 상위 서비스단에서 정수형 가게 번호(storeId)를 파라미터로 넘겨받아 XML 매퍼의 복합 JOIN 질의문 조건절(WHERE store_id = ?)에 대입하고, 연쇄적으로 결합된 users 테이블의 id 값과 menu 테이블의 name 명칭을 일괄 추출하여 다중 행의 ReviewResponseDTO 리스트 컬렉션 가방으로 반환 처리하는 기능
     */
    List<ReviewResponseDTO> findByStoreId(int storeId);

    /**
     * [회원 식별자 매핑 마이페이지 내역 조회]
     * 세션 검증부로부터 정수형 회원 번호(userId) 인자값을 수용하여 XML 매퍼 내의 질의 필터(WHERE user_id = ?)에 바인딩하고, 해당 사용자가 작성한 전체 리뷰 이력을 시간 역순(DESC)으로 정렬 및 조인 결합하여 가공된 다중 DTO 데이터 리스트 구조로 빌드해 리턴하는 기능
     */
    List<ReviewResponseDTO> findByUserId(int userId);

    /**
     * [리뷰 고유키 기준 원본 레코드 단건 추출]
     * 수정 폼이나 디테일 팝업 가동 시 정수형 리뷰 일련번호(reviewId)를 식별 키로 받아 단일 WHERE 조건 검색을 수행하고, 매핑되는 가공 전 순정 review 테이블 레코드 1건의 알맹이 변수들을 자바 Review 데이터 모델 객체 껍데기에 그대로 채워 단건 반환하는 기능
     */
    Review findById(int reviewId);

    /**
     * [리뷰 고유키 기준 영구 삭제 연산]
     * 뷰에서 전달된 정수형 리뷰 고유 번호(reviewId)를 수용하여 마이바티스 DELETE 질의어 파라미터로 전송하고, 데이터베이스 내부 review 테이블 내에서 해당 일련번호와 완벽히 일치하는 행 레코드 1건을 즉시 영구 삭제(DROP) 처리하는 기능
     */
    void deleteById(int reviewId);

    /**
     * [기존 리뷰 레코드 데이터 덮어쓰기 갱신]
     * 사용자가 화면 폼에서 새로 입력하여 갱신한 별점(rating), 글 내용(content), 이미지 경로(picture)가 반영된 가공 엔티티를 파라미터로 넘겨받아, 고유키(review_id)와 일치하는 대상 행 레코드의 컬럼 값들만 선택적으로 UPDATE 동기화 연산하는 기능
     */
    void update(Review updateReview);
}