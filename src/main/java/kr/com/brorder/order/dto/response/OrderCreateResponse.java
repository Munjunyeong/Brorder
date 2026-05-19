package kr.com.brorder.order.dto.response;

public class OrderCreateResponse {
    private Long orderId;
    private int totalPrice;

    public OrderCreateResponse(Long orderId, int totalPrice) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}