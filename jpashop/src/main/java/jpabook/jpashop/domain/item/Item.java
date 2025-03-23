package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hosikchoi
 * @class Item
 * @desc 추상 각채로 만든 item
 * @desc2 상속관계 전략을 부모 클래스에서 지정
 * @desc3 현재 싱글테이블 전략으로 진행, strategy 타입을 잡아야함.(joined-가장 정교한, single table, TableperClass-별도 테이블)
 * @since 2025-03-23
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //strategy 전략
@DiscriminatorColumn(name = "dtype") //DiscriminatorValue 으로 연계
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;

    private int price;

    private int stock;

    private int stockQuantity;

}
