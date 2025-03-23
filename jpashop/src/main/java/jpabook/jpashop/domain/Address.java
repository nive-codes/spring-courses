package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

/**
 * @author hosikchoi
 * @class Address
 * @desc 주소 객체
 * @since 2025-03-23
 */
@Embeddable //어딘가에 내장되어 있다.
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    //값 타입은 변경 불가능하게 설계 해야합니다.
    //jpa 스펙 상 생성, new로 생성하면 안됨.
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
