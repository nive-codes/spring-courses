package hello.core.lifecycle;

public class NetworkClient {

    private String url;

    public NetworkClient() {
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

}
