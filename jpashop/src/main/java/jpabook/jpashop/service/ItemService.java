package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.respository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hosikchoi
 * @class ItemService
 * @desc 상품 관리하는 service(위임만 하는 클래스이기 때문에 생각을 해볼 필요는 있다 - controller에서 그냥 Respository를 호출해서 써도 괜찮지 않을까?
 * @since 2025-03-25
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
  private final ItemRepository itemRepository;

  @Transactional //readonly를 피하기 위해 직접 애노테이션 달아야 함.
  public void saveItem(Item item) {
    itemRepository.save(item);
  }


  public List<Item> findItems(){
    return itemRepository.findAll();
  }

  public Item findOne(Long id) {
    return itemRepository.findOne(id);
  }


}
