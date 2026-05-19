package kr.com.brorder.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.com.brorder.order.dto.request.OrderCreateRequest;
import kr.com.brorder.order.mapper.OrderMapper;
import kr.com.brorder.order.model.Order;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public void insert(OrderCreateRequest request) {

        // ORDER INSERT (orderId 자동 생성됨)
        orderMapper.insert(request);

        // 🔥 여기 중요: MyBatis useGeneratedKeys로 orderId 들어와야 함
        Long orderId = request.getOrderId();

        // 👉 아직 ORDER_MENU는 다음 단계
        // System.out.println(orderId); // 확인용
    }

    @Override
    public List<Order> list() {
        return orderMapper.list();
    }
}