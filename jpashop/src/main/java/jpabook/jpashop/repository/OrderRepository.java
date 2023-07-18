package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        return em.createQuery("select o from Order o join o.member m"
                        + " where o.orderStatus = :status "
                        + " and m.name like :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setFirstResult(100) // 100 부터  시작
                .setMaxResults(1000) // 1000개 가져옴(페이징)
                .getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class // 하나의 쿼리로 order, member, delivery를 조인해서 select절에 다 넣고 한 번에 가져옴
        ).getResultList();
    }


    public List<Order> findAllWithItem() {
        return em.createQuery("select distinct o from Order o" + // distinct에 의해 order가 같은 값이면 중복 제거
                " join fetch o.member m" +
                " join fetch o.delivery d" +
                " join fetch o.orderItems oi" +
                " join fetch oi.item i", Order.class).getResultList();
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery(
                        "select o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d", Order.class // 하나의 쿼리로 order, member, delivery를 조인해서 select절에 다 넣고 한 번에 가져옴
                ).setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }
}
