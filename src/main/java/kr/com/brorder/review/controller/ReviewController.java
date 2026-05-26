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
     * URL 경로에서 가게 번호(storeId)를 받아 해당 매장에 등록된 전체 리뷰를 타임리프 화면에 바인딩함
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
     * 사용자가 작성한 데이터(DTO)를 받아 로그인 세션을 검증한 후 안전하게 데이터베이스에 저장함
     */
    @PostMapping("/write")
    public String writeReview(@ModelAttribute ReviewRequestDTO requestDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer loginUserId = (Integer) session.getAttribute("loginUserId");

        if (loginUserId == null) {
            return "redirect:/user/login";
        }

        reviewService.writeReview(requestDTO, loginUserId);

        return "redirect:/review/store/" + requestDTO.getStoreId();
    }

    /**
     * [내가 쓴 리뷰 목록 조회 화면]
     * 마이페이지 등에서 사용되며, 주소창에 유저 번호를 노출하지 않고 로그인 세션 정보만으로 본인의 리뷰 내역을 가져옴
     */
    @GetMapping("/my")
    public String showMyReviews(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer loginUserId = (Integer) session.getAttribute("loginUserId");

        if (loginUserId == null) {
            return "redirect:/user/login";
        }

        List<ReviewResponseDTO> myReviews = reviewService.getMyReviews(loginUserId);
        model.addAttribute("myReviewList", myReviews);

        return "review/my_reviews";
    }

    /**
     * [리뷰 삭제 처리]
     * 삭제 후 원래 보던 화면으로 자연스럽게 돌아가도록 분기 처리(마이페이지 또는 해당 가게 화면)를 수행함
     */
    @ResponseBody
    @PostMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable("reviewId") int reviewId,
                               @RequestParam("storeId") int storeId,
                               HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer loginUserId = (Integer) session.getAttribute("loginUserId");

        if (loginUserId == null) {
            return "<script>alert('로그인이 필요한 서비스입니다.'); location.href='/user/login';</script>";
        }

        reviewService.removeReview(reviewId, loginUserId);

        String redirectUrl = (storeId == 0) ? "/review/my" : "/review/store/" + storeId;

        return "<script>" +
                "alert('삭제되었습니다.');" +
                "location.href='" + redirectUrl + "';" +
                "</script>";
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
     * 사용자가 새로 수정한 평점, 내용, 사진 정보를 반영하고 본인 확인 세션 검증 후 업데이트를 완료함
     */
    @ResponseBody
    @PostMapping("/update/{reviewId}")
    public String updateReview(@PathVariable("reviewId") int reviewId,
                               @ModelAttribute ReviewRequestDTO requestDTO,
                               HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer loginUserId = (Integer) session.getAttribute("loginUserId");

        if (loginUserId == null) {
            return "<script>alert('로그인이 필요합니다.'); location.href='/user/login';</script>";
        }

        reviewService.modifyReview(reviewId, requestDTO, loginUserId);
        return "<script>" +
                "alert('글이 수정되었습니다.');" +
                "location.href='/review/store/" + requestDTO.getStoreId() + "';" +
                "</script>";
    }
}