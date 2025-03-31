package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author hosikchoi
 * @class ItemController
 * @desc [클래스 설명]
 * @since 2025-03-31
 */
@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/items/new")
  public String createForm(Model model) {
    model.addAttribute("form", new BookForm());
    return "items/createItemForm";
  }

  @PostMapping("/items/new")
  public String create(BookForm form){
    Book book = new Book(); //향후엔 setter를 제거하도록.
    book.setName(form.getName());
    book.setPrice(form.getPrice());
    book.setStockQuantity(form.getStockQuantity());
    book.setAuthor(form.getAuthor());
    book.setIsbn(form.getIsbn());

    itemService.saveItem(book);

    return "redirect:/";
  }

  @GetMapping("/items")
  public String list(Model model) {
    List<Item> items = itemService.findItems();
    model.addAttribute("items", items);
    return "items/itemList";

  }

  @GetMapping("/items/{itemId}/edit")
  public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
    Book item =(Book) itemService.findOne(itemId);
    BookForm form = new BookForm();
    form.setId(item.getId());
    form.setName(item.getName());
    form.setPrice(item.getPrice());
    form.setStockQuantity(item.getStockQuantity());
    form.setAuthor(item.getAuthor());
    form.setIsbn(item.getIsbn());
    model.addAttribute("form", form);
    return "items/updateItemForm";
  }

  @PostMapping("items/{itemId}/edit")
  public String update(@ModelAttribute("form") BookForm form, @PathVariable("itemId") Long itemId) {

//    Book book = new Book();
//    book.setId(form.getId());
//    book.setName(form.getName());
//    book.setPrice(form.getPrice());
//    book.setStockQuantity(form.getStockQuantity());
//    book.setAuthor(form.getAuthor());
//    book.setIsbn(form.getIsbn());
//
//    itemService.saveItem(book);

    itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

    return "redirect:/items";

  }

}
