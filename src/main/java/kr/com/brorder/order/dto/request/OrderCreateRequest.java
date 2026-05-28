package kr.com.brorder.order.dto.request;

import java.util.List;

public class OrderCreateRequest {

    // MyBatis generated key용
    private int orderId;

    private int userId;
    private int storeId;
    private String paymentMethod;
    private String requests;
    private int totalPrice;

    private List<OrderMenuCreateRequest> items;

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
    
    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}