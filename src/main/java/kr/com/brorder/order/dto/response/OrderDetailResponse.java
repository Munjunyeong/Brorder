package kr.com.brorder.order.dto.response;

import java.sql.Timestamp;
import java.util.List;

public class OrderDetailResponse {

    private int orderId;
    private int userId;
    private int storeId;
    private String paymentMethod;
    private int totalPrice;
    private String requests;
    private Timestamp createdAt;

    private List<OrderMenuResponse> items;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRequests() {
        return requests;
    }

    public void setRequests(String requests) {
        this.requests = requests;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderMenuResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderMenuResponse> items) {
        this.items = items;
    }
}