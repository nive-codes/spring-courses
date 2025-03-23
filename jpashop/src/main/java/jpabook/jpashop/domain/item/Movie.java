package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * @author hosikchoi
 * @class Movie
 * @desc item을 상속받는 movie 객체
 * @since 2025-03-23
 */
@Entity
@DiscriminatorValue("M")
public class Movie extends Item {
    private String director;
    private String actor;

}
