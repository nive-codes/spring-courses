package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hosikchoi
 * @class Album
 * @desc item을 상속받는 앨범 객체
 * @since 2025-03-23
 */
@Entity
@DiscriminatorValue("A")
@Getter @Setter
public class Album  extends Item{
    private String artist;
    private String etc;

}
