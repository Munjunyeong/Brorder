package kr.com.brorder.review.domain;

/**
 * [리뷰 등록/수정 요청 DTO]
 * 브라우저 화면의 폼(Form) 양식에서 사용자가 입력하여 서버로 전송하는 데이터를 담아두는 객체임
 */
public class ReviewRequestDTO {

    private int storeId;   // 사용자가 리뷰를 작성하는 대상 가게의 고유 식별 번호임
    private int menuId;    // 사용자가 주문하여 리뷰를 남기는 대상 메뉴의 고유 식별 번호임
    private int rating;    // 사용자가 선택하여 제출한 별점 점수(1~5점)임
    private String content; // 사용자가 입력창에 직접 작성한 리뷰 텍스트 내용임
    private String picture; // 사용자가 첨부하여 업로드된 사진 파일의 경로 또는 파일명임

    // HTML 화면의 <input type="file" name="reviewImageFile"> 과 매핑되는 멀티파트 파일 객체 그릇임
    private org.springframework.web.multipart.MultipartFile reviewImageFile;

    /**
     * 스프링 커맨드 객체 바인딩 및 JSON 파싱을 위해 사용하는 기본 생성자임
     */
    public ReviewRequestDTO() {
    }

    /**
     * 데이터 주입 및 테스트 코드 작성 시 데이터를 한 번에 채우기 위한 생성자임
     */
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

    //파일 업로드 객체를 주입받고 추출하기 위한 필수 Getter / Setter 메서드 구역임
    public org.springframework.web.multipart.MultipartFile getReviewImageFile() {
        return reviewImageFile;
    }

    public void setReviewImageFile(org.springframework.web.multipart.MultipartFile reviewImageFile) {
        this.reviewImageFile = reviewImageFile;
    }
}