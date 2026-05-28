package kr.com.brorder.order.model;

public class OrderMenu {

    private Long orderMenuId;
    private Long orderId;
    private Long menuId;
    private Long optionId;
    private int price;

    public Long getOrderMenuId() {
        return orderMenuId;
    }

    public void setOrderMenuId(Long orderMenuId) {
        this.orderMenuId = orderMenuId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}