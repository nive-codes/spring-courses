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
    private zipcode;
}
