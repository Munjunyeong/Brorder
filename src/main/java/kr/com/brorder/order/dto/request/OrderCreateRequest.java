package kr.com.brorder.order.dto.request;

import java.util.List;

public class OrderCreateRequest {

    // 🔥 DB에서 생성 후 채워지는 값 (MyBatis용)
    private Long orderId;

    private Long userId;
    private Long storeId;
    private String paymentMethod;
    private String requests;

    private List<OrderMenuCreateRequest> items;

    // getter / setter

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRequests() {
        return requests;
    }

    public void setRequests(String requests) {
        this.requests = requests;
    }

    public List<OrderMenuCreateRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderMenuCreateRequest> items) {
        this.items = items;
    }
}