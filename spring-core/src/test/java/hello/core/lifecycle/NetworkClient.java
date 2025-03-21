package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean /*초기화 빈 상속 */, DisposableBean {

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

    /*property 의존관계 주입이 끝나면 실행되는 메서드*/
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NetworkClient afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");

    }

    /*종료될때*/
    @Override
    public void destroy() throws Exception {
        System.out.println("NetworkClient destroy");
        disconnect();
    }
}
