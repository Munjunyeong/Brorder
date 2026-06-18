package kr.com.brorder.order.service; //오더 서비스

import java.util.List;

import kr.com.brorder.menu.model.MenuOption;
import kr.com.brorder.order.model.Order;

public interface OrderService {

    void insert(Order order);

    List<Order> list(Long userId);

    Order item(Long orderId);

    void delete(Long orderId);

}