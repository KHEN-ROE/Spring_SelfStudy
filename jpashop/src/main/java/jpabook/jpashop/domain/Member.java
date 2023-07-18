package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded // 내장 타입
    private Address address;

//    @JsonIgnore // json으로 반환하지 않음. 그러나 이렇게 하면 안 됨. 어떤 api에서는 이게 필요할 수 있음.
                // 또한 화면 출력을 위한 로직이 추가됨. 엔티티로 의존관계가 들어와야 하는데, 엔티티에서 의존관계가 나가고 있음
                // 이러면 양방향으로 의존관계가 걸리면서 애플리케이션을 수정하기가 어렵다.
    @OneToMany(mappedBy = "member") // 연관관계에서 주인이 아닌 거울. Order테이블의 member 필드에 의해 맵핑됨. 읽기 전용이 됨
    private List<Order> orders = new ArrayList<>();

}
