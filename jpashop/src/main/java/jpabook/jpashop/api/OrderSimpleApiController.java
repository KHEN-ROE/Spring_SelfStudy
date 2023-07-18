package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * XToOne (ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); // order에서 getMember를 가져오는 것 까지는 프록시 객체이다(일종의 가짜 객체). db에 쿼리가 안 날아간다. getName()하면 Lazy 강제 초기화
            order.getDelivery().getAddress();
        }
        return all;
    }

    @GetMapping("api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() { // 바로 List로 반환하지 않고 result로 한번 감싸서 리턴해야한다.
        // ORDER 2개
        // N + 1 -> 1 + 회원 N  + 배송 N
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        // 엔티티를 dto로 변환

        return orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
    }

    // 패치조인
    @GetMapping("api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() { // 패치조인
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        // 엔티티 -> dto
        List<SimpleOrderDto> collect = orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
        return collect;
    }

    @GetMapping("api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }


    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDate orderDate;
        private OrderStatus status;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // LAZY 초기화(영속성 컨텍스트가 ID를 가지고 찾아온다. 없으면 DB에 쿼리를 날린다.)
            orderDate = order.getOrderDate();
            status = order.getOrderStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화
        }
    }
}
