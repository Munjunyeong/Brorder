package kr.com.brorder.order.service;  //오더 서비스임플

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.com.brorder.order.dao.OrderDao;
import kr.com.brorder.order.model.Order;
import kr.com.brorder.order.model.OrderMenu;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Transactional
    @Override
    public void insert(Order order) {

        int totalPrice = 0;
        if (order.getItems() != null) {
            for (OrderMenu item : order.getItems()) {
                if (item.getPrice() != null) {
                    totalPrice += item.getPrice();
                }
            }
        }
        
        // 기본 배달팁 3,000원 추가 (메뉴가 1개 이상일 때)
        if (totalPrice > 0) {
            totalPrice += 3000;
        }
        
        order.setTotalPrice(totalPrice);

        orderDao.insertOrder(order);

        if (order.getItems() != null) {
            for (OrderMenu item : order.getItems()) {
                item.setOrderId((long) order.getOrderId());
                
                // 만약 optionsJson이 있다면 (여러 옵션 처리용)
                // 현재 DB 구조는 order_menu에 option_id 하나만 저장 가능함.
                // 첫 번째 옵션이라도 저장하도록 하거나, 비즈니스 로직에 맞게 처리.
                // 여기서는 JSON 문자열을 파싱하지 않고, 프론트에서 넘어온 option_id가 있다면 그것을 우선 사용.
                
                orderDao.insertOrderMenu(item);
            }
        }
    }

    @Override
    public List<Order> list(Long userId) {
        return orderDao.selectOrderList(userId);
    }

    @Override
    public Order item(Long orderId) {
        Order order = orderDao.selectOrderById(orderId);
        if (order != null) {
            order.setItems(orderDao.selectOrderMenuListByOrderId(orderId));
        }
        return order;
    }

    @Transactional
    @Override
    public void delete(Long orderId) {
        orderDao.deleteOrderMenuByOrderId(orderId);
        orderDao.deleteOrder(orderId);
    }
}