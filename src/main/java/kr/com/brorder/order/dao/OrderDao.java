package kr.com.brorder.order.dao;

import java.util.List;
import kr.com.brorder.order.model.Order;
import kr.com.brorder.order.model.OrderMenu;

public interface OrderDao {
    void insertOrder(Order order);
    void insertOrderMenu(OrderMenu orderMenu);
    List<Order> selectOrderList(Long userId);
    Order selectOrderById(Long orderId);
    List<OrderMenu> selectOrderMenuListByOrderId(Long orderId);
    void deleteOrder(Long orderId);
    void deleteOrderMenuByOrderId(Long orderId);
}
