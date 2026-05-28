package kr.com.brorder.review.controller;

import kr.com.brorder.review.domain.ReviewRequestDTO;
import kr.com.brorder.review.domain.ReviewResponseDTO;
import kr.com.brorder.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 * [리뷰 도메인 메인 컨트롤러]
 * 사용자의 리뷰 관련 모든 요청(조회, 작성, 수정, 삭제)을 받아 처리하고 해당하는 HTML 화면을 띄워주는 클래스임
 */
@Controller
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 생성자 주입을 통해 비즈니스 로직을 처리하는 ReviewService 객체를 연결함
     */
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * [가게별 리뷰 목록 조회 화면]
     * 세션 로그인 여부와 상관없이 비회원에게도 데이터베이스에 저장된 해당 매장의 전체 리뷰 리스트 데이터를 추출하여 타임리프 화면에 송신함
     */
    @GetMapping("/store/{storeId}")
    public String showStoreReviews(@PathVariable("storeId") int storeId, Model model) {
        List<ReviewResponseDTO> reviews = reviewService.getStoreReviews(storeId);

        model.addAttribute("reviewList", reviews);
        model.addAttribute("currentStoreId", storeId);

        return "review/store_reviews";
    }

    /**
     * [신규 리뷰 등록 처리]
     * 세션 가방 내부의 고유 번호를 확인하여 수용하고 매장 주소로 새로고침 리다이렉트를 강제 수행함
     */
    @PostMapping("/write")
    public String writeReview(@ModelAttribute ReviewRequestDTO requestDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();

        kr.com.brorder.users.Users loginUser = (kr.com.brorder.users.Users) session.getAttribute("users");

        /* [교정 완] 조원들의 순정 로그인 주소인 /login 명세로 정밀 수정함 */
        if (loginUser == null) {
            return "redirect:/login";
        }

        Long dbUserid = loginUser.getUserid();

        if (dbUserid == null) {
            return "redirect:/login";
        }

        Integer loginUserId = dbUserid.intValue();

        reviewService.writeReview(requestDTO, loginUserId);

        return "redirect:/review/store/" + requestDTO.getStoreId();
    }

    /**
     * [내가 쓴 리뷰 목록 조회 화면]
     * 마이페이지 필터링을 가동하기 위해 인증 객체 식별 번호를 빌드하여 모델 가방에 담아 송신함
     */
    @GetMapping("/my")
    public String showMyReviews(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();

        kr.com.brorder.users.Users loginUser = (kr.com.brorder.users.Users) session.getAttribute("users");

        if (loginUser == null) {
            return "redirect:/login";
        }

        Long dbUserid = loginUser.getUserid();

        if (dbUserid == null) {
            return "redirect:/login";
        }

        Integer loginUserId = dbUserid.intValue();

        List<ReviewResponseDTO> myReviews = reviewService.getMyReviews(loginUserId);
        model.addAttribute("myReviewList", myReviews);

        return "review/my_reviews";
    }

    /**
     * [리뷰 삭제 처리]
     * 권한 일치 여부를 검증하고 삭제 트랜잭션을 구동한 뒤 기존 매장 상세 정보창으로 동기화 복귀함
     */
    @PostMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable("reviewId") int reviewId,
                               @RequestParam("storeId") int storeId,
                               HttpServletRequest request) {
        HttpSession session = request.getSession();

        kr.com.brorder.users.Users loginUser = (kr.com.brorder.users.Users) session.getAttribute("users");

        if (loginUser == null) {
            return "redirect:/login";
        }

        Long dbUserid = loginUser.getUserid();

        if (dbUserid == null) {
            return "redirect:/login";
        }

        Integer loginUserId = dbUserid.intValue();

        reviewService.removeReview(reviewId, loginUserId);

        return "redirect:/review/store/" + storeId;
    }

    /**
     * [리뷰 수정 팝업/화면 호출]
     * 수정하려는 기존 리뷰의 데이터를 찾아와 폼 양식에 채워준 뒤 수정 페이지를 첢
     */
    @GetMapping("/update/{reviewId}")
    public String showUpdateForm(@PathVariable("reviewId") int reviewId, Model model) {
        kr.com.brorder.review.domain.Review review = reviewService.getReviewById(reviewId);
        model.addAttribute("review", review);
        return "review/update_review";
    }

    /**
     * [리뷰 수정 처리]
     * 변경된 입력 파라미터 세트를 받아 영속성 레이어에 병합 연산을 지시하고 원래 보던 화면으로 다이렉트 복귀함
     */
    @PostMapping("/update/{reviewId}")
    public String updateReview(@PathVariable("reviewId") int reviewId,
                               @ModelAttribute ReviewRequestDTO requestDTO,
                               @RequestParam("storeId") int storeId,
                               HttpServletRequest request) {
        HttpSession session = request.getSession();

        kr.com.brorder.users.Users loginUser = (kr.com.brorder.users.Users) session.getAttribute("users");

        /* [오류 수리 마감] 멀티파트 파일 첨부 시 세션이 우회 튕김 처리되던 에러 주소를 순정 주소인 /login으로 전면 수정 완료함 */
        if (loginUser == null) {
            return "redirect:/login";
        }

        Long dbUserid = loginUser.getUserid();

        if (dbUserid == null) {
            return "redirect:/login";
        }

        Integer loginUserId = dbUserid.intValue();

        reviewService.modifyReview(reviewId, requestDTO, loginUserId);

        return "redirect:/review/store/" + storeId;
    }
}