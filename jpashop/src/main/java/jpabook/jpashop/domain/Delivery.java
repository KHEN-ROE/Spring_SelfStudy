package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // ordinal 은 숫자로 매겨지는 건데, 중간에 enum이 추가되면 순서가 밀리게 됨. 그래서 안 씀
    private DeliveryStatus deliveryStatus; // READY, COMP
}
