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
 * - 컨트롤러 → 서비스 → DAO → DB 흐름 담당
 */
@Controller
public class ReviewController {

    private final ReviewService reviewService;

    @Value("${kopo.upload.path}")
    private String path;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // =========================
    // 리뷰 작성 페이지
    // =========================
    @GetMapping("/review/write")
    public String showReviewWriteForm(
            @RequestParam int orderId,
            @RequestParam int storeId,
            Model model,
            HttpServletRequest request) {

        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");

        if (loginUser == null || loginUser.getUserid() == null) {
            return "redirect:/login";
        }

        ReviewResponseDTO orderInfo = reviewService.getOrderReviewInfo(orderId);

        model.addAttribute("storeId", orderInfo.getStoreId());
        model.addAttribute("storeName", orderInfo.getStoreName());
        model.addAttribute("menuId", orderInfo.getMenuId());
        model.addAttribute("menuName", orderInfo.getMenuName());

        return "review/write";
    }

    // =========================
    // 가게 리뷰 목록
    // =========================
    @GetMapping("/review/store/{storeId}")
    public String showStoreReviews(@PathVariable int storeId, Model model) {

        List<ReviewResponseDTO> reviews = reviewService.getStoreReviews(storeId);
        if (reviews == null) reviews = new ArrayList<>();

        List<MenuOptionDTO> menuOptions = reviewService.getMenusByStoreId(storeId);
        if (menuOptions == null) menuOptions = new ArrayList<>();

        model.addAttribute("reviewList", reviews);
        model.addAttribute("currentStoreId", storeId);
        model.addAttribute("menuOptions", menuOptions);

        return "review/store_reviews";
    }

    // =========================
    // 리뷰 등록
    // =========================
    @PostMapping("/review/write")
    public String writeReview(@ModelAttribute ReviewRequestDTO requestDTO,
                              HttpServletRequest request) {

        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");

        if (loginUser == null || loginUser.getUserid() == null) {
            return "redirect:/login";
        }

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

    // =========================
    // 내 리뷰 조회
    // =========================
    @GetMapping("/users/my/review")
    public String showMyReviews(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");

        if (loginUser == null || loginUser.getUserid() == null) {
            return "redirect:/login";
        }

        int userId = loginUser.getUserid().intValue();

        System.out.println("[내 리뷰 조회] userId = " + userId);

        List<ReviewResponseDTO> myReviews = reviewService.getMyReviews(userId);

        if (myReviews == null) myReviews = new ArrayList<>();

        System.out.println("[내 리뷰 조회] count = " + myReviews.size());

        model.addAttribute("myReviewList", myReviews);

        return "review/my_reviews";
    }

    // =========================
    // 리뷰 삭제
    // =========================
    @PostMapping("/review/delete/{reviewId}")
    public String deleteReview(@PathVariable int reviewId,
                               @RequestParam int storeId,
                               HttpServletRequest request) {

        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");

        if (loginUser == null || loginUser.getUserid() == null) {
            return "redirect:/login";
        }

        int userId = loginUser.getUserid().intValue();

        Review targetReview = reviewService.getReviewById(reviewId);
        if (targetReview != null && targetReview.getPicture() != null && !targetReview.getPicture().isEmpty()) {
            File fileToDelete = new File(path, targetReview.getPicture());
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
        }

        reviewService.removeReview(reviewId, userId);

        return "redirect:/review/store/" + storeId;
    }

    // =========================
    // 리뷰 수정 페이지
    // =========================
    @GetMapping("/review/update/{reviewId}")
    public String showUpdateForm(@PathVariable int reviewId, Model model) {

        Review review = reviewService.getReviewById(reviewId);

        model.addAttribute("review", review);

        return "review/update_review";
    }

    // =========================
    // 리뷰 수정 처리 (이전 파일 물리 파기 로직 조립 완료)
    // =========================
    @PostMapping("/review/update/{reviewId}")
    public String updateReview(@PathVariable int reviewId,
                               @ModelAttribute ReviewRequestDTO requestDTO,
                               @RequestParam int storeId,
                               HttpServletRequest request) {

        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");

        if (loginUser == null || loginUser.getUserid() == null) {
            return "redirect:/login";
        }

        int userId = loginUser.getUserid().intValue();

        MultipartFile imageFile = requestDTO.getReviewImageFile();

        // 사용자가 수정창에서 '새로운 사진 파일'을 첨부하여 가방이 채워졌을 때만 분기문 진입
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // [수정 핵심 구역] 새 파일 저장 전, DB에서 기존 리뷰에 등록되어 있던 옛날 파일명을 먼저 조회
                Review oldReview = reviewService.getReviewById(reviewId);
                if (oldReview != null && oldReview.getPicture() != null && !oldReview.getPicture().isEmpty()) {
                    File oldFile = new File(path, oldReview.getPicture());
                    if (oldFile.exists()) {
                        oldFile.delete(); // 하드디스크에서 예전 사진 파일 물리 삭제 처분
                    }
                }

                File dir = new File(path);
                if (!dir.exists()) dir.mkdirs();

                String savedFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

                imageFile.transferTo(new File(dir, savedFileName));

                requestDTO.setPicture(savedFileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        reviewService.modifyReview(reviewId, requestDTO, userId);

        return "redirect:/review/store/" + storeId;

    }
}