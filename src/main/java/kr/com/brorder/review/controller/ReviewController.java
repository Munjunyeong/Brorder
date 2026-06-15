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
            @RequestParam(value = "storeId", defaultValue = "1") int storeId,
            @RequestParam(value = "storeName", defaultValue = "홍콩반점") String storeName,
            @RequestParam(value = "menuId", defaultValue = "1") int menuId,
            @RequestParam(value = "menuName", defaultValue = "짜장면") String menuName,
            Model model,
            HttpServletRequest request) {

        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");

        if (loginUser == null || loginUser.getUserid() == null) {
            return "redirect:/login";
        }

        model.addAttribute("storeId", storeId);
        model.addAttribute("storeName", storeName);
        model.addAttribute("menuId", menuId);
        model.addAttribute("menuName", menuName);

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
    // 리뷰 수정 처리
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

        reviewService.modifyReview(reviewId, requestDTO, userId);

        return "redirect:/review/store/" + storeId;

    }
}