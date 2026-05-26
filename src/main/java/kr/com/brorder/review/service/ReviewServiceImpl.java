package kr.com.brorder.review.service;

import kr.com.brorder.review.domain.Review;
import kr.com.brorder.review.domain.ReviewRequestDTO;
import kr.com.brorder.review.domain.ReviewResponseDTO;
import kr.com.brorder.review.mapper.ReviewMapper; // [오류 해결 포인트] 이 import 문이 있어야 Mapper를 인식합니다.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // [역할] 스프링 부트가 이 객체를 비즈니스 로직 핵심 컴포넌트로 등록하게 합니다.
public class ReviewServiceImpl implements ReviewService {

    // [오류 해결 포인트] 데이터베이스 접근을 위한 매퍼 인터페이스 변수를 선언합니다.
    private final ReviewMapper reviewMapper;

    // [오류 해결 포인트] 스프링 부트가 생성자를 통해 ReviewMapper 객체를 자동으로 조립(주입)해 줍니다.
    @Autowired
    public ReviewServiceImpl(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    @Override
    public void writeReview(ReviewRequestDTO requestDTO, int loginUserId) {
        // [로직] 화면에서 넘어온 DTO 바구니를 순수 DB 엔티티(Review) 객체로 알맞게 옮겨 담습니다.
        Review review = new Review();
        review.setStoreId(requestDTO.getStoreId());
        review.setMenuId(requestDTO.getMenuId());
        review.setUserId(loginUserId); // [보안] 서버 세션의 안전한 유저 ID를 세팅합니다.
        review.setRating(requestDTO.getRating());
        review.setContent(requestDTO.getContent());
        review.setPicture(requestDTO.getPicture());

        // [정보 이동] 변환 완료된 데이터를 Mapper를 통해 DB에 INSERT 요청을 보냅니다.
        reviewMapper.save(review);
    }

    @Override
    public List<ReviewResponseDTO> getStoreReviews(int storeId) {
        // [정보 이동] 가게 ID 조건에 맞는 JOIN 결과를 DB에서 꺼내와 그대로 상위 레이어(Controller)로 토스합니다.
        return reviewMapper.findByStoreId(storeId);
    }

    @Override
    public List<ReviewResponseDTO> getMyReviews(int loginUserId) {
        // [정보 이동] 로그인한 사용자 고유 번호를 토대로 본인이 작성한 리뷰만 DB에서 뽑아 컨트롤러로 넘깁니다.
        return reviewMapper.findByUserId(loginUserId);
    }

    @Override
    public void removeReview(int reviewId, int loginUserId) {
        // [로직 1] 삭제 전에 해당 리뷰가 진짜 존재하는지, 그리고 누가 썼는지 확인하기 위해 DB에서 먼저 꺼내봅니다.
        Review targetReview = reviewMapper.findById(reviewId);

        if (targetReview == null) {
            throw new IllegalArgumentException("존재하지 않는 리뷰입니다.");
        }

        // [핵심 검증] 리뷰를 작성한 유저 고유 ID와 현재 삭제를 누른 사용자의 세션 유저 ID가 일치하는지 비교합니다.
        if (targetReview.getUserId() != loginUserId) {
            throw new IllegalStateException("본인이 작성한 리뷰만 삭제할 수 있습니다.");
        }

        // [정보 이동] 검증이 성공하면 안심하고 Mapper를 호출해 해당 행을 DB에서 완전히 지워버립니다.
        reviewMapper.deleteById(reviewId);
    }

    /* ========================================================================
     * [종민님 요청사항 반영] 여기서부터 수정 기능을 온전히 구동하기 위해 추가된 비즈니스 구현부입니다.
     * ======================================================================== */

    @Override
    public Review getReviewById(int reviewId) {
        // [정보 이동] 수정 창에 채워줄 원본 데이터를 마이바티스 단건 조회 매퍼를 통해 긁어와 토스합니다.
        return reviewMapper.findById(reviewId);
    }

    @Override
    public void modifyReview(int reviewId, ReviewRequestDTO requestDTO, int loginUserId) {
        // [로직 1] 수정 전에 해당 리뷰가 진짜 존재하는지 디비에서 먼저 영속성 확인을 진행합니다.
        Review targetReview = reviewMapper.findById(reviewId);

        if (targetReview == null) {
            throw new IllegalArgumentException("존재하지 않는 리뷰입니다.");
        }

        // [보안 검증] 임의로 주소를 쳐서 남의 글을 수정하려는 악성 접근을 막기 위해 작성자 검증을 동일하게 탑재합니다.
        if (targetReview.getUserId() != loginUserId) {
            throw new IllegalStateException("본인이 작성한 리뷰만 수정할 수 있습니다.");
        }

        // [로직 2] 검증 완료 시 새로 화면에서 넘겨받은 평점, 텍스트 내용, 첨부 이미지 데이터로 엔티티를 새롭게 빌드합니다.
        Review updateReview = new Review();
        updateReview.setReviewId(reviewId); // 어떤 글을 고칠지 결정하는 WHERE 조건 키값
        updateReview.setRating(requestDTO.getRating());
        updateReview.setContent(requestDTO.getContent());
        updateReview.setPicture(requestDTO.getPicture());

        // [정보 이동] 새로 정비된 수정용 엔티티를 매퍼를 통해 DB에 UPDATE 명령으로 밀어 넣습니다.
        reviewMapper.update(updateReview);
    }
}