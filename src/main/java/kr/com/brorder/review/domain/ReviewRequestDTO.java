package kr.com.brorder.review.domain;

public class ReviewRequestDTO {

    // [정보 이동] 화면에서 사용자가 선택한 가게의 고유 번호가 넘어옵니다.
    private int storeId;
    // [정보 이동] 화면에서 사용자가 선택한 메뉴의 고유 번호가 넘어옵니다.
    private int menuId;
    // [정보 이동] 사용자가 준 별점 점수(1~5)가 넘어옵니다.
    private int rating;
    // [정보 이동] 사용자가 작성한 리뷰 텍스트 내용이 넘어옵니다.
    private String content;
    // [정보 이동] 업로드한 사진의 파일 경로 또는 파일명이 문자열로 넘어옵니다.
    private String picture;

    public ReviewRequestDTO() {
    }

    public ReviewRequestDTO(int storeId, int menuId, int rating, String content, String picture) {
        this.storeId = storeId;
        this.menuId = menuId;
        this.rating = rating;
        this.content = content;
        this.picture = picture;
    }

    public int getStoreId() { return storeId; }
    public void setStoreId(int storeId) { this.storeId = storeId; }

    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }
}