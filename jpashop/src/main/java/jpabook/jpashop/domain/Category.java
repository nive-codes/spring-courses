package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hosikchoi
 * @class Category
 * @desc [클래스 설명]
 * @since 2025-03-23
 */
@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    //다 대 다 관계 샘플
    //다 대 다도 연관관계 주인 필요
    @ManyToMany
    @JoinTable(name = "category_item",                          //중간 테이블(category_item)에서 중간 테이블 조인 컬럼(FK, category_id)고,
            joinColumns = @JoinColumn(name = "category_id"),   //중간 테이블(키만 존재하는 테이블로 엮어줌 - category_id, item_id 모두 FK 사용)
            inverseJoinColumns = @JoinColumn(name = "item_id"))  //category_item에 들어가는 item쪽으로 들어가는 컬럼을 매핑
    private List<Item> items = new ArrayList<>();



    /**
     *
     *  다른 entity 묶듯이 해주면 스스로에게 의존관계? 형성 가능
     *
     * */


    //나(부모)는 한개
    @ManyToOne(fetch = FetchType.LAZY)  //가급적이면 XToOne은 쓰지말고 꼭 지연로딩(LAZY)로 처리)
    @JoinColumn(name = "parent_id")
    private Category parent;

    //나(자식)은 여러개
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();



}
