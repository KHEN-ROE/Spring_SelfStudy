package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // jpa의 내장 타입. 어딘가에 내장될 수 있다는 뜻.
@Getter
public class Address { // 값 타입은 변경 불가능하게 설계해야함. 세터 제거하고 생성자에서 값 초기화하여 변경 불가능하게 만듦

    private String city;
    private String street;
    private String zipcode;

    protected Address() {

    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
