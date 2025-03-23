package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hosikchoi
 * @class Book
 * @desc item을 상속받는 book 객체
 * @since 2025-03-23
 */
@Entity
@Getter
@Setter
@DiscriminatorValue("B")
public class Book extends Item{
    private String author;
    private String isbn;
}
