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
      em.merge(item);
    }
  }

  public Item findOne(Long id) {
    return em.find(Item.class, id);
  }

  public List<Item> findAll() {
    return em.createQuery("select i from Item i",Item.class).getResultList();
  }


}
