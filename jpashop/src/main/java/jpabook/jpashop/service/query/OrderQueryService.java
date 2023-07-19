//package jpabook.jpashop.service.query;
//
//import jpabook.jpashop.domain.Order;
//import jpabook.jpashop.repository.order.simplequery.OrderApiController;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//import static java.util.stream.Collectors.toList;
//
//@Transactional(readOnly = true)
//public class OrderQueryService {
//
//    @GetMapping("/api/v3/orders")
//    public List<OrderApiController.OrderDto> ordersV3() { // 패치조인으로 쿼리 한 방만 나가도록 튜닝
//        List<Order> orders = orderRepository.findAllWithItem();
//
//        List<OrderApiController.OrderDto> collect = orders.stream().map(o -> new OrderApiController.OrderDto(o)).collect(toList()); // 생성자에 넘긴다
//        return collect;
//    }
//}
