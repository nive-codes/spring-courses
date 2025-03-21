package hello.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok hellolombok = new HelloLombok();
        hellolombok.setName("hello");
        hellolombok.setAge(20);

        String name=hellolombok.getName();
        System.out.println(name);

//        System.out.println(helloLombk.toString());
    }
}
