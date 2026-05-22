package kr.com.brorder.order.mapper;

import java.util.List;

import kr.com.brorder.order.model.OrderMenu;
import org.apache.ibatis.annotations.Mapper;

import kr.com.brorder.order.dto.request.OrderCreateRequest;
import kr.com.brorder.order.model.Order;

@Mapper
public interface OrderMapper {

    void insert(OrderCreateRequest request);

    List<Order> list();

    Order item(Long orderId);

    List<OrderMenu> listByOrderId(Long orderId);
}