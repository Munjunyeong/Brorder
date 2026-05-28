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
     * [교정 완료] 종민님의 Users 소스코드 규격에 맞춰 Long 타입을 Integer로 안전하게 다운캐스팅(intValue)하여 서비스 단에 적재 마감함
     */
    @PostMapping("/write")
    public String writeReview(@ModelAttribute ReviewRequestDTO requestDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();

        // 1. 세션에서 users 가방을 열어 종민님의 진짜 Users 클래스 타입으로 형변환하여 추출함
        kr.com.brorder.users.Users loginUser = (kr.com.brorder.users.Users) session.getAttribute("users");

        if (loginUser == null) {
            return "redirect:/login";
        }

        // 2. [완벽 동기화] Users.java에 설계된 순정 getUserid() 메소드를 직격 호출함
        Long dbUserid = loginUser.getUserid();

        // [근본 에러 방어벽] 로그인 시점에 번호가 빠져서 null이 들어왔다면 500 예외를 터트리지 않고 로그인창으로 후방 격리함
        if (dbUserid == null) {
            return "redirect:/login";
        }

        // 3. 기존의 reviewService.writeReview 파라미터 규격(Integer)에 맞게 Long 값을 안전하게 int형으로 변환하여 주입함
        Integer loginUserId = dbUserid.intValue();

        reviewService.writeReview(requestDTO, loginUserId);

        return "redirect:/review/store/" + requestDTO.getStoreId();
    }

    /**
     * [내가 쓴 리뷰 목록 조회 화면]
     * [교정 완료] 정공법 Users 캐스팅 문법 및 Long 타입 변환 필터를 완벽하게 이식함
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
     * [교정 완료] 정석 캐스팅 필터를 통해 안전하게 본인 식별 번호를 비교 검증하여 삭제 유도함
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
     * [교정 완료] 수정 메소드 내부의 구버전 잔재 로직을 싹 제거하고 순정 Users 데이터 연동 규격으로 깔끔하게 매칭 마감함
     */
    @PostMapping("/update/{reviewId}")
    public String updateReview(@PathVariable("reviewId") int reviewId,
                               @ModelAttribute ReviewRequestDTO requestDTO,
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

        reviewService.modifyReview(reviewId, requestDTO, loginUserId);

        return "redirect:/review/store/" + storeId;
    }
}