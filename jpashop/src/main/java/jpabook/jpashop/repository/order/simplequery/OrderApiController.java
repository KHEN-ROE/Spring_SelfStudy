package jpabook.jpashop.repository.order.simplequery;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    // 지양해야할 방법(엔티티 노출 금지)
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            //프록시 강제 초기화
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        // 엔티티를 dto로 변환
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList()); // 생성자에 넘긴다
        return collect;
    }


    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() { // 패치조인으로 쿼리 한 방만 나가도록 튜닝
        List<Order> orders = orderRepository.findAllWithItem();

        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList()); // 생성자에 넘긴다
        return collect;
    }


    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                        @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList()); // 생성자에 넘긴다
        return collect;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        OrderQueryRepository.findOrderQueryDtos();
    }


    @Data
    static class OrderDto {

        private Long orderId;
        private String name;
        private LocalDate orderDate;
        private OrderStatus orderStatus;
        private Address address;
        //        private List<OrderItem> orderItems; // dto를 반환할 때 dto안에 이렇게 엔티티가 있으면 안 된다.
        private List<OrderItemDto> orderItems; // dto를 반환할 때 dto안에 이렇게 엔티티가 있으면 안 된다.


        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getOrderStatus();
            address = order.getDelivery().getAddress();
            // 마찬가지로 똑같이 엔티티를 dto로 변환
            orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem)).collect(Collectors.toList());
        }

        @Data
        static class OrderItemDto {

            private String itemName; // 상품명
            private int orderPrice; // 주문가격
            private int count; // 주문 수량

            public OrderItemDto(OrderItem orderItem) {
                itemName = orderItem.getItem().getName();
                orderPrice = orderItem.getOrderPrice();
                count = orderItem.getCount();
            }
        }
    }
}
