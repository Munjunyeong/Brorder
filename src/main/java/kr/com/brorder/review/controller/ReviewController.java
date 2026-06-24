package kr.com.brorder.review.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.com.brorder.review.domain.MenuOptionDTO;
import kr.com.brorder.review.domain.Review;
import kr.com.brorder.review.domain.ReviewRequestDTO;
import kr.com.brorder.review.domain.ReviewResponseDTO;
import kr.com.brorder.review.service.ReviewService;
import kr.com.brorder.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 리뷰 컨트롤러
 *
 * 역할
 * 사용자 요청을 받아 서비스로 전달하는 계층
 * 화면 반환 및 입력 데이터 전달 담당
 *
 * 구조
 * Controller Service DAO DB
 *
 * 보안 특징
 * 사용자 식별은 반드시 세션 기반으로 처리
 * 클라이언트 전달 값은 신뢰하지 않음
 */
@Controller
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 파일 업로드 경로
     * application properties 설정값 사용
     */
    @Value("${kopo.upload.path}")
    private String path;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * 리뷰 작성 페이지 이동
     *
     * 주문 정보 기반으로 리뷰 작성 화면 데이터 구성
     */
    @GetMapping("/review/write")
    public String showReviewWriteForm(
            @RequestParam int orderId,
            @RequestParam int storeId,
            Model model,
            HttpServletRequest request) {

        ReviewResponseDTO orderInfo = reviewService.getOrderReviewInfo(orderId);

        model.addAttribute("storeId", orderInfo.getStoreId());
        model.addAttribute("storeName", orderInfo.getStoreName());
        model.addAttribute("menuId", orderInfo.getMenuId());
        model.addAttribute("menuName", orderInfo.getMenuName());

        return "review/write";
    }

    /**
     * 가게 리뷰 목록 조회
     *
     * 비회원도 접근 가능한 공개 페이지
     */
    @GetMapping("/review/store/{storeId}")
    public String showStoreReviews(@PathVariable int storeId, Model model) {

        List<ReviewResponseDTO> reviews = reviewService.getStoreReviews(storeId);
        if (reviews == null) reviews = new ArrayList<>();

        List<MenuOptionDTO> menuOptions = reviewService.getMenusByStoreId(storeId);
        if (menuOptions == null) menuOptions = new ArrayList<>();

        // [수정점] 리뷰가 있으면 DB에 연동된 진짜 상호명을 쓰고, 리뷰가 0개면 임시 상호명 분기 처리
        String storeName = "홍콩반점";
        if (storeId == 2) storeName = "치킨천국"; // ◀ 혹시 2번 가게 이름이 치킨천국이면 이런 식으로 분기 추가 가능!

        if (!reviews.isEmpty()) {
            storeName = reviews.get(0).getStoreName();
        }

        model.addAttribute("reviewList", reviews);
        model.addAttribute("currentStoreId", storeId);
        model.addAttribute("storeName", storeName); // ◀ 가방에 이름 쏙 넣기
        model.addAttribute("menuOptions", menuOptions);

        return "review/store_reviews";
    }

    /**
     * 리뷰 등록 처리
     *
     * 이미지 업로드 처리 후 서비스로 전달
     * 로그인 사용자는 세션 기반으로 식별
     */
    @PostMapping("/review/write")
    public String writeReview(@ModelAttribute ReviewRequestDTO requestDTO,
                              HttpServletRequest request) {

        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");
        int userId = loginUser.getUserid().intValue();

        MultipartFile imageFile = requestDTO.getReviewImageFile();

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                File dir = new File(path);
                if (!dir.exists()) dir.mkdirs();

                String savedFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

                imageFile.transferTo(new File(dir, savedFileName));

                requestDTO.setPicture(savedFileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        reviewService.writeReview(requestDTO, userId);

        return "redirect:/review/store/" + requestDTO.getStoreId();
    }

    /**
     * 내 리뷰 조회
     *
     * 로그인 사용자 기준 리뷰 목록 조회
     */
    @GetMapping("/users/my/review")
    public String showMyReviews(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");
        int userId = loginUser.getUserid().intValue();

        List<ReviewResponseDTO> myReviews = reviewService.getMyReviews(userId);

        if (myReviews == null) myReviews = new ArrayList<>();

        model.addAttribute("myReviewList", myReviews);

        return "review/my_reviews";
    }

    /**
     * 리뷰 삭제 처리
     *
     * 서비스에서 권한 검증 후 삭제 수행
     */
    @PostMapping("/review/delete/{reviewId}")
    public String deleteReview(@PathVariable int reviewId,
                               @RequestParam int storeId,
                               HttpServletRequest request) {

        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");
        int userId = loginUser.getUserid().intValue();

        reviewService.removeReview(reviewId, userId);

        return "redirect:/review/store/" + storeId;
    }

    /**
     * 리뷰 수정 페이지 이동
     *
     * 기존 리뷰 데이터를 화면에 출력
     */
    @GetMapping("/review/update/{reviewId}")
    public String showUpdateForm(@PathVariable int reviewId, Model model) {

        Review review = reviewService.getReviewById(reviewId);

        model.addAttribute("review", review);

        return "review/update_review";
    }

    /**
     * 리뷰 수정 처리
     *
     * 이미지 변경 여부에 따라 파일 처리 분기
     */
    @PostMapping("/review/update/{reviewId}")
    public String updateReview(@PathVariable int reviewId,
                               @ModelAttribute ReviewRequestDTO requestDTO,
                               @RequestParam int storeId,
                               HttpServletRequest request) {

        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");
        int userId = loginUser.getUserid().intValue();

        MultipartFile imageFile = requestDTO.getReviewImageFile();

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                File dir = new File(path);
                if (!dir.exists()) dir.mkdirs();

                String savedFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                imageFile.transferTo(new File(dir, savedFileName));

                requestDTO.setPicture(savedFileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            Review currentReview = reviewService.getReviewById(reviewId);
            if (currentReview != null) {
                requestDTO.setPicture(currentReview.getPicture());
            }
        }

        reviewService.modifyReview(reviewId, requestDTO, userId);

        return "redirect:/review/store/" + storeId;
    }
}