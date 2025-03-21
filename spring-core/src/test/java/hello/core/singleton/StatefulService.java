package hello.core.singleton;

/*싱글톤 객체는 상태 유지했을 때 어떻게 될 것인가.*/
public class StatefulService {
    private int price;  //상태를 유지하는 필드

    public int order(String name, int price){
        System.out.println("name = " + name + " price = " + price);
        return price;
    }

    public int getPrice(){
        return price;
    }
}
