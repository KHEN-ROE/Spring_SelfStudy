package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입
    private Address address;

    @OneToMany(mappedBy = "member") // 연관관계에서 주인이 아닌 거울. Order테이블의 member 필드에 의해 맵핑됨. 읽기 전용이 됨
    private List<Order> orders = new ArrayList<>();




}
