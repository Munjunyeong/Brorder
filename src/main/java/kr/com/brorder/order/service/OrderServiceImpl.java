package kr.com.brorder.order.service;

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