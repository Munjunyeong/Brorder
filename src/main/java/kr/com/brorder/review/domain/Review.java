package kr.com.brorder.review.domain;

import java.time.LocalDateTime;

public class Review {

    private int reviewId;       // review_id INT (PK)
    private int storeId;        // store_id INT (FK)
    private int userId;         // user_id INT (FK)
    private int menuId;         // menu_id INT (FK)
    private int rating;         // rating INT
    private String content;     // content VARCHAR(255)
    private String picture;     // picture VARCHAR(255)
    private LocalDateTime createdData; // created_data TIMESTAMP

    // 기본 생성자
    public Review() {
    }

    // 모든 필드를 포함한 생성자
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

    // Getter / Setter 메서드
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