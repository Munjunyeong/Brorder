package kr.com.brorder.order.model; //모델

import java.sql.Timestamp;
import java.util.List;

public class Order {

    private int orderId;
    private int userId;
    private int storeId;
    private String paymentMethod;
    private int totalPrice;
    private String requests;
    private Timestamp createdAt;

    // 추가 필드
    private String storeName;
    private String firstMenuName;
    private int extraCount;

    private List<OrderMenu> items;

    public List<OrderMenu> getItems() {
        return items;
    }

    public void setItems(List<OrderMenu> items) {
        this.items = items;
    }

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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getFirstMenuName() {
        return firstMenuName;
    }

    public void setFirstMenuName(String firstMenuName) {
        this.firstMenuName = firstMenuName;
    }

    public int getExtraCount() {
        return extraCount;
    }

    public void setExtraCount(int extraCount) {
        this.extraCount = extraCount;
    }
}