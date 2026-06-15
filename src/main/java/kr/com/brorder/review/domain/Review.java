package kr.com.brorder.review.domain;

import java.time.LocalDateTime;

// 데이터베이스 review 테이블 매핑 순수 도메인 엔티티 객체
public class Review {

    private int reviewId;       // 리뷰 고유 식별 번호 (기본키)
    private int storeId;        // 리뷰 작성 가게 ID
    private int userId;         // 리뷰 작성 회원 ID
    private int menuId;         // 주문 메뉴 ID
    private int rating;         // 부여된 별점 점수 (1점 ~ 5점)
    private String content;     // 리뷰 내용 텍스트
    private String picture;     // 서버 저장용 랜덤 이미지 파일명
    private LocalDateTime createdData; // 리뷰 등록 날짜 및 시간

    // MyBatis 자동 매핑용 기본 생성자
    public Review() {
    }

    // 데이터 일괄 주입용 초기화 생성자
    public Review(int reviewId, int storeId, int userId, int menuId, int rating, String content, String picture, LocalDateTime createdData) {
        this.reviewId = reviewId;
        this.storeId = storeId;
        this.userId = userId;
        this.menuId = menuId;
        this.rating = rating;
        this.content = content;
        this.picture = picture;
        this.createdData = createdData;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public LocalDateTime getCreatedData() {
        return createdData;
    }

    public void setCreatedData(LocalDateTime createdData) {
        this.createdData = createdData;
    }
}
// 06/15 커밋 테스트