package hello.core.singleton;

public class SingletonServiceTest {

    /*자기 내부에 static으로 선언해서 class level이기 때문에 하나만 생성됨*/
    /*jvm 실행될 때 static을 실행해서 instance생성해서 참조에 넣어줌.*/
    private static final SingletonServiceTest instance = new SingletonServiceTest();

    /*조회할 때 사용*/
    public static SingletonServiceTest getInstance() {
        return instance;
    }

    // private 생성자 (외부에서 호출할 수 없도록 막음)
    private SingletonServiceTest() {
        // 생성자 내용
    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
