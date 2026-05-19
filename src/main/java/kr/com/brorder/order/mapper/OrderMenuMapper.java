package kr.com.brorder.order.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMenuMapper {

    void insert(Long orderId, Long menuId, Long optionId, int price);
}