package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hosikchoi
 * @class Member
 * @desc 회원 ENTITY
 * @since 2025-03-23
 */
@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded //내장되어 있는 객체이다.
    private Address address;

//    @JsonIgnore //이렇게 하면 api에서 보이진 않음. 이래서 entity를 직접 내려주는게 좋지 않다.
    // 1:N 관계다~
    @OneToMany(mappedBy = "member")  //order 테이블에 있는 member 필드에 의해서 매핑이 되는 것이다. 라고 매핑된 거울이라고 읽기 전용이 되는 것.
    private List<Order> orders = new ArrayList<>();
}
