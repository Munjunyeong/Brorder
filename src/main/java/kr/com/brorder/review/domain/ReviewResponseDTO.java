package kr.com.brorder.review.domain;

import java.time.LocalDateTime;

/**
 * [리뷰 조회 응답 DTO]
 * 데이터베이스에서 조회한 리뷰 데이터와 JOIN 결과를 담아서 브라우저 HTML 화면(타임리프)으로 전달하는 객체임
 */
public class ReviewResponseDTO {

    private int reviewId;       // 화면에 렌더링하고 수정/삭제 시 식별자로 활용할 리뷰 고유 번호임
    private int rating;         // 타임리프 화면에서 별점(★) 아이콘 개수를 출력할 평점 데이터임
    private String content;     // 사용자가 등록한 리뷰 본문 텍스트 내용임
    private String picture;     // 화면에 리뷰 이미지를 띄우기 위한 첨부 파일명 또는 경로 문자열임
    private LocalDateTime createdData; // 리뷰 카드의 작성 일시(연.월.일 시:분)를 포맷팅하여 보여주기 위한 시간 데이터임

    private String userIdName;  // users 테이블과 JOIN하여 가져온 실제 유저의 로그인 아이디 또는 닉네임 정보임
    private String menuName;    // menu 테이블과 JOIN하여 가져온 실제 주문 메뉴 명칭(예: 짜장면, 치킨 등)임

    /**
     * 마이바티스(MyBatis) 맵퍼 플러그인 등에서 결과를 자동으로 바인딩하기 위한 기본 생성자임
     */
    public ReviewResponseDTO() {
    }

    /**
     * 데이터베이스 JOIN 쿼리 조회 결과를 한 번에 주입하여 객체를 생성하는 생성자임
     */
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