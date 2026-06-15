package kr.com.brorder.order.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import kr.com.brorder.order.model.Order;
import kr.com.brorder.order.model.OrderMenu;

@Repository
public class OrderDaoImpl implements OrderDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "kr.com.brorder.order.OrderMapper.";

    public OrderDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void insertOrder(Order order) {
        sqlSession.insert(NAMESPACE + "insertOrder", order);
    }

    @Override
    public void insertOrderMenu(OrderMenu orderMenu) {
        sqlSession.insert(NAMESPACE + "insertOrderMenu", orderMenu);
    }

    @Override
    public List<Order> selectOrderList(Long userId) {
        return sqlSession.selectList(NAMESPACE + "selectOrderList", userId);
    }

    @Override
    public Order selectOrderById(Long orderId) {
        return sqlSession.selectOne(NAMESPACE + "selectOrderById", orderId);
    }

    @Override
    public List<OrderMenu> selectOrderMenuListByOrderId(Long orderId) {
        return sqlSession.selectList(NAMESPACE + "selectOrderMenuListByOrderId", orderId);
    }

    @Override
    public void deleteOrder(Long orderId) {
        sqlSession.delete(NAMESPACE + "deleteOrder", orderId);
    }

    @Override
    public void deleteOrderMenuByOrderId(Long orderId) {
        sqlSession.delete(NAMESPACE + "deleteOrderMenuByOrderId", orderId);
    }
}
