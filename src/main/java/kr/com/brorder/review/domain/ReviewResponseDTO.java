package kr.com.brorder.review.domain;

import java.time.LocalDateTime;

/**
 * [리뷰 조회 응답 DTO]
 * 데이터베이스에서 조회한 리뷰 데이터와 JOIN 결과를 담아서 브라우저 HTML 화면(타임리프)으로 전달하는 객체임
 */
public class ReviewResponseDTO {

    private int reviewId;
    private int storeId;
    private int menuId;
    private int rating;
    private String content;
    private String picture;
    private LocalDateTime createdData;

    private String userIdName;
    private String storeName;   // 추가
    private String menuName;

    /**
     * 기본 생성자
     */
    public ReviewResponseDTO() {
    }

    /**
     * 전체 생성자
     */
    public ReviewResponseDTO(
            int reviewId,
            int storeId,
            int menuId,
            int rating,
            String content,
            String picture,
            LocalDateTime createdData,
            String userIdName,
            String storeName,
            String menuName
    ) {
        this.reviewId = reviewId;
        this.storeId = storeId;
        this.menuId = menuId;
        this.rating = rating;
        this.content = content;
        this.picture = picture;
        this.createdData = createdData;
        this.userIdName = userIdName;
        this.storeName = storeName;
        this.menuName = menuName;
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

    public String getUserIdName() {
        return userIdName;
    }

    public void setUserIdName(String userIdName) {
        this.userIdName = userIdName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}