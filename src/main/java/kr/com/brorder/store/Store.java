package kr.com.brorder.store;

import java.time.LocalDateTime;

public class Store {
    private Integer storeId;
    private Long userid;
    private String name;
    private String category;
    private String address;
    private String phone;
    private LocalDateTime createdData;
    private String status;
    private String titleImage;

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Long getUserid() {return userid;}

    public void setUserid(Long userid) {this.userid =userid;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedData() {
        return createdData;
    }

    public void setCreatedData(LocalDateTime createdData) {
        this.createdData = createdData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
