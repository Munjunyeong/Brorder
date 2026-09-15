package kr.com.brorder.menu;

public class MenuOption {
    private Integer optionId;   // PK
    private Integer groupId;    // FK: 소속된 옵션 그룹 ID
    private String name;        // 옵션명 (예: 특 사이즈, 맛보기 수육)
    private Integer price;      // 추가 가격 (예: 2000, 5000)

    public MenuOption() {}

    public Integer getOptionId() { return optionId; }
    public void setOptionId(Integer optionId) { this.optionId = optionId; }
    public Integer getGroupId() { return groupId; }
    public void setGroupId(Integer groupId) { this.groupId = groupId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
}
