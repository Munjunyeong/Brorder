package kr.com.brorder.order.mapper;

import kr.com.brorder.order.model.OrderMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMenuMapper {

    void insert(Long orderId, Long menuId, Long optionId, int price);

    List<OrderMenu> listByOrderId(Long orderId);
}