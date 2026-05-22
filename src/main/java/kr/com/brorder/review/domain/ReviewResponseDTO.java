package kr.com.brorder.review.domain;

import java.time.LocalDateTime;

public class ReviewResponseDTO {

    // [정보 이동] DB에서 조회한 리뷰의 고유 번호를 화면으로 넘깁니다.
    private int reviewId;
    // [정보 이동] DB review 테이블의 rating 점수를 화면으로 넘깁니다.
    private int rating;
    // [정보 이동] DB review 테이블의 content 글 내용을 화면으로 넘깁니다.
    private String content;
    // [정보 이동] DB review 테이블의 저장된 사진 경로를 화면으로 넘깁니다.
    private String picture;
    // [정보 이동] DB review 테이블의 작성 시간을 화면으로 넘깁니다.
    private LocalDateTime createdData;

    // [중요 - 관계 반영] ERD의 users 테이블과 JOIN하여 얻은 실제 유저의 'id'(예: hong123)를 화면에 보여주기 위해 넘깁니다.
    private String userIdName;
    // [중요 - 관계 반영] ERD의 menu 테이블과 JOIN하여 얻은 실제 메뉴의 'name'(예: 후라이드치킨)을 화면에 보여주기 위해 넘깁니다.
    private String menuName;

    public ReviewResponseDTO() {
    }

    public ReviewResponseDTO(int reviewId, int rating, String content, String picture, LocalDateTime createdData, String userIdName, String menuName) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.content = content;
        this.picture = picture;
        this.createdData = createdData;
        this.userIdName = userIdName;
        this.menuName = menuName;
    }

    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public LocalDateTime getCreatedData() { return createdData; }
    public void setCreatedData(LocalDateTime createdData) { this.createdData = createdData; }

    public String getUserIdName() { return userIdName; }
    public void setUserIdName(String userIdName) { this.userIdName = userIdName; }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }
}