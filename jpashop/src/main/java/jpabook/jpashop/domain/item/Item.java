package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    private int stockQuantity;

    //거의 쓰이지는 않음....실제로도 그렇다.
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /*************************객체 지향적으로 처리. setter를 쓰지말고 객체가 있는 곳에서 비즈니스 로직을 만들어서 처리, 응집도 향상**************************/
    /**
     * stock 증가 수량
     * @param quantity
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 증가 수량
     * @param quantity
     */
    public void removeStock(int quantity) {
        int restStock = stockQuantity - quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
//        System.out.println("재고 확인 : "+stockQuantity);
        this.stockQuantity = restStock;
//        System.out.println("재고 확인 : "+stockQuantity);


    }


}
