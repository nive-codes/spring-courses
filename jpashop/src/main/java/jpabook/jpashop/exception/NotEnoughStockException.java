package jpabook.jpashop.exception;

/**
 * @author hosikchoi
 * @class NotEnoughStockException
 * @desc
 * @since 2025-03-25
 */
public class NotEnoughStockException extends RuntimeException {


    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }


}
