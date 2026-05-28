package kr.com.brorder.review.domain;

import java.time.LocalDateTime;

/**
 * [리뷰 데이터베이스 엔티티]
 * 데이터베이스의 review 테이블과 1:1로 매핑되는 순수 자바 객체(Domain)임
 */
public class Review {

    private int reviewId;       // 리뷰 고유 식별 번호 (기본키)
    private int storeId;        // 리뷰가 작성된 가게 고유 번호 (외래키)
    private int userId;         // 리뷰를 작성한 사용자 고유 번호 (외래키)
    private int menuId;         // 주문한 메뉴 고유 번호 (외래키)
    private int rating;         // 사용자가 부여한 별점 점수
    private String content;     // 사용자가 작성한 리뷰 텍스트 내용
    private String picture;     // 서버에 업로드된 첨부 이미지 파일명
    private LocalDateTime createdData; // 리뷰가 최종 등록된 생성 일시

    /**
     * 마이바티스(MyBatis) 등 프레임워크에서 객체를 생성할 때 사용하는 기본 생성자임
     */
    public Review() {
    }

    /**
     * 데이터베이스 조회 데이터나 임시 데이터를 한 번에 담아서 객체를 생성하는 생성자임
     */
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