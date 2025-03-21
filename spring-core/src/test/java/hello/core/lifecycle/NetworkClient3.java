package hello.core.lifecycle;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class NetworkClient3 {

    private String url;

    public NetworkClient3() {
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 메시지");

    }


    public void setUrl(String url) {
            this.url = url;
            System.out.println("url set 처리");
    }

    /*서비스 시작 시 호출*/
    public void connect(){
        System.out.println("connect:" + url);
    }

    public void call(String messages){
        System.out.println("call:" + url + " messages = " + messages);

    }

    /*서비스 종료 시 호출*/
    public void disconnect(){
        System.out.println("close : " + url);
    }

    /*property 의존관계 주입이 끝나면 실행되는 메서드*/
    @PostConstruct
    public void init() throws Exception {
        System.out.println("NetworkClient init");
        connect();
        call("초기화 연결 메시지");

    }

    /*종료될때*/
    @PreDestroy
    public void close() throws Exception {
        System.out.println("NetworkClient close");
        disconnect();
    }
}
