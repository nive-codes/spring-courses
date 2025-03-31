package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hosikchoi
 * @class BookForm
 * @desc book dto
 * @since 2025-03-31
 */

@Getter @Setter
public class BookForm {

  private Long id;
  private String name;
  private int price;
  private int stockQuantity;
  private String author;
  private String isbn;
}
