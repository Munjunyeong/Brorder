package kr.com.brorder.review.controller;

import kr.com.brorder.review.domain.ReviewRequestDTO;
import kr.com.brorder.review.domain.ReviewResponseDTO;
import kr.com.brorder.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller // [역할] 브라우저의 URL 요청을 받아서 화면(HTML)을 띄워주는 컨트롤러로 등록합니다.
@RequestMapping("/review") // [설정] 이 컨트롤러 내부의 모든 주소는 앞에 /review 가 기본으로 붙습니다.
public class ReviewController {

    private final ReviewService reviewService;

    // [의존성 주입] 우리가 구현해 둔 ReviewServiceImpl 비즈니스 객체를 자동으로 연결합니다.
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * [역할] 특정 가게의 리뷰 목록 화면을 보여줍니다.
     * https://m.kpedia.jp/w/5351 GET http://localhost:8080/review/store/가게번호
     * [정보 이동] URL에서 추출한 storeId를 Service로 넘겨 JOIN된 리뷰 목록을 가져온 뒤, 타임리프 화면(Model)에 태워 보냅니다.
     */
    @GetMapping("/store/{storeId}")
    public String showStoreReviews(@PathVariable("storeId") int storeId, Model model) {
        List<ReviewResponseDTO> reviews = reviewService.getStoreReviews(storeId);

        // [화면 전달] DB에서 긁어온 리뷰 리스트를 HTML 화면에서 'reviewList'라는 이름으로 꺼내 쓸 수 있게 보냅니다.
        model.addAttribute("reviewList", reviews);
        model.addAttribute("currentStoreId", storeId);

        return "review/store_reviews"; // [화면 경로] src/main/resources/templates/review/store_reviews.html 뷰 파일로 이동
    }

    /**
     * [역할] 사용자가 화면에 작성한 리뷰를 제출(등록) 버튼을 눌렀을 때 처리합니다.
     * https://m.kpedia.jp/w/5351 POST http://localhost:8080/review/write
     * [정보 이동] HTML Form태그에서 작성된 값(DTO)을 받아 세션 유저 정보와 합친 뒤 Service의 저장 로직으로 던집니다.
     */
    @PostMapping("/write")
    public String writeReview(@ModelAttribute ReviewRequestDTO requestDTO) {
        // [주의] 실제 서비스 시에는 세션(Session)에서 로그인한 사용자 고유 번호를 꺼내와야 합니다.
        // 현재는 연동 전이므로 임시 테스트용 유저 고유번호인 '1'번 유저로 세팅하여 동작시킵니다.
        int mockLoginUserId = 1;

        // [정보 이동] 작성 내용과 유저 번호를 비즈니스 레이어로 토스합니다.
        reviewService.writeReview(requestDTO, mockLoginUserId);

        // [화면 이동] 리뷰 작성이 완료되면 해당 가게의 리뷰 목록 페이지로 화면을 새로고침(리다이렉트) 시킵니다.
        return "redirect:/review/store/" + requestDTO.getStoreId();
    }

    /**
     * [역할] 마이페이지 같은 곳에서 내가 쓴 리뷰 목록만 모아서 화면에 보여줍니다.
     * https://m.kpedia.jp/w/5351 GET http://localhost:8080/review/my
     * [정보 이동] 세션의 유저 ID로 조회한 본인 리뷰 응답 데이터를 화면(Model)에 실어 보냅니다.
     */
    @GetMapping("/my")
    public String showMyReviews(Model model) {
        // 여기도 로그인 연동 전이므로 임시 테스트용 '1'번 유저로 세팅합니다.
        int mockLoginUserId = 1;

        List<ReviewResponseDTO> myReviews = reviewService.getMyReviews(mockLoginUserId);
        model.addAttribute("myReviewList", myReviews);

        return "review/my_reviews"; // [화면 경로] src/main/resources/templates/review/my_reviews.html 뷰 파일로 이동
    }

    /**
     * [역할] 리뷰 삭제 버튼을 눌렀을 때 삭제를 처리합니다.
     * [변경점] 삭제 후 무조건 메인('/')으로 가던 로직을, 넘어온 storeId 파라미터를 활용해
     * 보던 가게 화면('/review/store/가게번호')으로 제자리 리다이렉트 시키도록 주소를 정밀 수정했습니다.
     */
    @ResponseBody
    @PostMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable("reviewId") int reviewId,
                               @RequestParam("storeId") int storeId) {
        // 로그인 연동 전 임시 테스트 유저 번호 세팅
        int mockLoginUserId = 1;

        // [정보 이동] 삭제 로직 실행
        reviewService.removeReview(reviewId, mockLoginUserId);

        // 마이페이지(storeId=0)에서 삭제 버튼을 눌렀다면 마이페이지 주소로 보내고,
        // 가게 리뷰 화면에서 눌렀다면 해당 가게 리뷰 주소로 돌아가도록 분기 처리합니다.
        String redirectUrl = (storeId == 0) ? "/review/my" : "/review/store/" + storeId;

        // 자바스크립트 알림창 출력 후 보던 제자리 페이지로 이동하는 태그 반환
        return "<script>" +
                "alert('삭제되었습니다.');" +
                "location.href='" + redirectUrl + "';" +
                "</script>";
    }

    /* ========================================================================
     * 수정 기능 구역 (기존과 100% 동일 유지)
     * ======================================================================== */

    @GetMapping("/update/{reviewId}")
    public String showUpdateForm(@PathVariable("reviewId") int reviewId, Model model) {
        kr.com.brorder.review.domain.Review review = reviewService.getReviewById(reviewId);
        model.addAttribute("review", review);
        return "review/update_review";
    }

    @ResponseBody
    @PostMapping("/update/{reviewId}")
    public String updateReview(@PathVariable("reviewId") int reviewId,
                               @ModelAttribute ReviewRequestDTO requestDTO) {
        int mockLoginUserId = 1;
        reviewService.modifyReview(reviewId, requestDTO, mockLoginUserId);
        return "<script>" +
                "alert('글이 수정되었습니다.');" +
                "location.href='/review/store/" + requestDTO.getStoreId() + "';" +
                "</script>";
    }
}