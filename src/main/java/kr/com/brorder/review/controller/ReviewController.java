package kr.com.brorder.review.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.com.brorder.review.domain.Review;
import kr.com.brorder.review.domain.ReviewRequestDTO;
import kr.com.brorder.review.domain.ReviewResponseDTO;
import kr.com.brorder.review.service.ReviewService;
import kr.com.brorder.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

// 리뷰 관련 모든 요청 처리 및 화면 매핑 컨트롤러
@Controller
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    // 생성자 주입으로 리뷰 서비스 객체 연결
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 가게별 전체 리뷰 목록 조회 및 화면 송신
    @GetMapping("/store/{storeId}")
    public String showStoreReviews(@PathVariable("storeId") int storeId, Model model) {
        List<ReviewResponseDTO> reviews = reviewService.getStoreReviews(storeId);
        model.addAttribute("reviewList", reviews);
        model.addAttribute("currentStoreId", storeId);
        return "review/store_reviews";
    }

    // 신규 리뷰 정보 및 첨부 파일 서버 저장 등록 처리
    @PostMapping("/write")
    public String writeReview(@ModelAttribute ReviewRequestDTO requestDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");

        // 비회원인 경우 로그인 페이지로 리다이렉트
        if (loginUser == null) {
            return "redirect:/login";
        }

        Long dbUserid = loginUser.getUserid();
        if (dbUserid == null) {
            return "redirect:/login";
        }

        Integer loginUserId = dbUserid.intValue();

        // 폼 양식에서 전송된 멀티파트 이미지 파일 추출
        MultipartFile imageFile = requestDTO.getReviewImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // 물리 파일 보관용 외부 폴더 경로 설정 및 생성
                String uploadDir = "C:/upload/images/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // 파일 이름 중복 방지용 UUID 랜덤 키 결합
                String originalFileName = imageFile.getOriginalFilename();
                String savedFileName = UUID.randomUUID().toString() + "_" + originalFileName;

                // 서버 실제 디스크 경로에 바이너리 파일 저장 실행
                File serverFile = new File(uploadDir + savedFileName);
                imageFile.transferTo(serverFile);

                // 업로드 완료된 고유 파일명을 DTO 가방에 저장
                requestDTO.setPicture(savedFileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        reviewService.writeReview(requestDTO, loginUserId);
        return "redirect:/review/store/" + requestDTO.getStoreId();
    }

    // 로그인 세션 유저가 작성한 마이페이지 리뷰 리스트 조회
    @GetMapping("/my")
    public String showMyReviews(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");

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

    // 특정 리뷰 식별 번호 삭제 트랜잭션 호출
    @PostMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable("reviewId") int reviewId,
                               @RequestParam("storeId") int storeId,
                               HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");

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

    // 수정 대상 리뷰의 원본 데이터를 담아 수정 폼 화면 호출
    @GetMapping("/update/{reviewId}")
    public String showUpdateForm(@PathVariable("reviewId") int reviewId, Model model) {
        Review review = reviewService.getReviewById(reviewId);
        model.addAttribute("review", review);
        return "review/update_review";
    }

    // 수정한 리뷰 데이터 및 새로 첨부한 이미지 파일 반영 수정 처리
    @PostMapping("/update/{reviewId}")
    public String updateReview(@PathVariable("reviewId") int reviewId,
                               @ModelAttribute ReviewRequestDTO requestDTO,
                               @RequestParam("storeId") int storeId,
                               HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");

        if (loginUser == null) {
            return "redirect:/login";
        }

        Long dbUserid = loginUser.getUserid();
        if (dbUserid == null) {
            return "redirect:/login";
        }

        Integer loginUserId = dbUserid.intValue();

        // 수정 폼에서 새로 첨부한 이미지 파일 존재 여부 확인
        MultipartFile imageFile = requestDTO.getReviewImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String uploadDir = "C:/upload/images/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String originalFileName = imageFile.getOriginalFilename();
                String savedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
                File serverFile = new File(uploadDir + savedFileName);
                imageFile.transferTo(serverFile);

                requestDTO.setPicture(savedFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        reviewService.modifyReview(reviewId, requestDTO, loginUserId);
        return "redirect:/review/store/" + storeId;
    }
}