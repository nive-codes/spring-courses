package jpabook.jpashop.respository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hosikchoi
 * @class ItemRepository
 * @desc 아이템을 관리하는 repository
 * @since 2025-03-25
 */

@Repository
@RequiredArgsConstructor
public class ItemRepository {

  private final EntityManager em;

  public void save(Item item) {
    if(item.getId() == null) {
      em.persist(item);
    } else{
      em.merge(item); //merge해서 나온 Item Result = em.merge(item)은 result는 영속성 관리 되나 item은 관리안됨
      //  내가 변경하고자 하는 값만 변경이 불가능하다(빈값은 null로 변경되는 등, 변경되선 안되는 값도 변경이 되어버린다.)
    }
  }

  public Item findOne(Long id) {
    return em.find(Item.class, id);
  }

  public List<Item> findAll() {
    return em.createQuery("select i from Item i",Item.class).getResultList();
  }


}
