package kr.com.brorder.menu.model;

public class Menu {
    private Integer menuId;     // PK: 메뉴 고유 번호
    private Integer storeId;    // FK: 이 메뉴가 속한 판매처(가게)의 고유 번호
    private String name;        // 메뉴 이름 (예: 황금올리브 치킨)
    private String content;     // 메뉴 소개 (예: 바삭바삭한 식감이 일품인 후라이드)
    private String picture;     // 메뉴 사진 파일명 (실제 이미지가 아닌 서버에 저장된 파일 이름)
    private Integer price;      // 메뉴 가격 (예: 20000)

    // 기본 생성자
    public Menu() {}

    // Getter & Setter
    public Integer getMenuId() { return menuId; }
    public void setMenuId(Integer menuId) { this.menuId = menuId; }

    public Integer getStoreId() { return storeId; }
    public void setStoreId(Integer storeId) { this.storeId = storeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
}
