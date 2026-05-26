package kr.com.brorder.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.com.brorder.order.dto.request.OrderCreateRequest;
import kr.com.brorder.order.dto.request.OrderMenuCreateRequest;
import kr.com.brorder.order.dto.response.OrderDetailResponse;
import kr.com.brorder.order.dto.response.OrderMenuResponse;
import kr.com.brorder.order.mapper.OrderMapper;
import kr.com.brorder.order.mapper.OrderMenuMapper;
import kr.com.brorder.order.model.Order;
import kr.com.brorder.order.model.OrderMenu;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderMenuMapper orderMenuMapper;

    public OrderServiceImpl(OrderMapper orderMapper,
                            OrderMenuMapper orderMenuMapper) {
        this.orderMapper = orderMapper;
        this.orderMenuMapper = orderMenuMapper;
    }

    @Transactional
    @Override
    public void insert(OrderCreateRequest request) {

        // 1️⃣ ORDER 저장
        orderMapper.insert(request);

        // 2️⃣ 생성된 orderId 가져오기
        int orderId = request.getOrderId();

        // 3️⃣ ORDER_MENU 저장
        for (OrderMenuCreateRequest item : request.getItems()) {

            orderMenuMapper.insert(
                    orderId,
                    item.getMenuId(),
                    item.getOptionId(),
                    item.getPrice()
            );
        }
    }

    @Override
    public List<Order> list() {
        return orderMapper.list();
    }

    @Override
    public OrderDetailResponse item(Long orderId) {

        // ORDER 조회
        Order order = orderMapper.item(orderId);

        // ORDER_MENU 조회
        List<OrderMenu> orderMenus =
                orderMenuMapper.listByOrderId(orderId);

        // 응답 DTO 생성
        OrderDetailResponse response =
                new OrderDetailResponse();

        response.setOrderId(order.getOrderId());
        response.setUserId(order.getUserId());
        response.setStoreId(order.getStoreId());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setTotalPrice(order.getTotalPrice());
        response.setRequests(order.getRequests());
        response.setCreatedData(order.getCreatedData());

        // 메뉴 리스트 변환
        List<OrderMenuResponse> items =
                new ArrayList<>();

        for (OrderMenu orderMenu : orderMenus) {

            OrderMenuResponse item =
                    new OrderMenuResponse();

            item.setOrderMenuId(orderMenu.getOrderMenuId());
            item.setOrderId(orderMenu.getOrderId());
            item.setMenuId(orderMenu.getMenuId());
            item.setOptionId(orderMenu.getOptionId());
            item.setPrice(orderMenu.getPrice());

            items.add(item);
        }

        response.setItems(items);

        return response;
    }
}