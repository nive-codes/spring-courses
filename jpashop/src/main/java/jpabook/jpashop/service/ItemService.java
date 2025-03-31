package jpabook.jpashop.service;

import jpabook.jpashop.controller.BookForm;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.respository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

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

  @Transactional
  public void updateItem(Long itemId, String name, int price , int stockQuantity ) {
    Item findItem = itemRepository.findOne(itemId); //영속성 컨텍스트로 관리되는 item에 데이터를 바꿔주면 transaction이 끝날때 변경감지 -> flush가 일어남
    findItem.setPrice(price);
    findItem.setName(name);
    findItem.setStockQuantity(stockQuantity);
    //더티 체킹으로 데이터 변경(transaction에서 끝날때 flush로 db에 직접 반영이 되는 형태)


  }


  public List<Item> findItems(){
    return itemRepository.findAll();
  }

  public Item findOne(Long id) {
    return itemRepository.findOne(id);
  }


}
