package kr.com.brorder.order.service;

import java.util.List;

import kr.com.brorder.order.dto.request.OrderCreateRequest;
import kr.com.brorder.order.dto.response.OrderDetailResponse;
import kr.com.brorder.order.model.Order;

public interface OrderService {

    void insert(OrderCreateRequest request);

    List<Order> list();

    OrderDetailResponse item(Long orderId);

    void delete(Long orderId);
}