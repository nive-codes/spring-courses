package hello.hello_artifact.domain;

/*domain 요구사항*/
/*JPA를 사용할 때는 엔티리를 매핑해줘야함.*/
/*JPA는 객체와 ORM이라는 기술*/
/*O ojbect, R relational(관계형DB를 뜻하는듯?) + M Mapping*/

import jakarta.persistence.*;

/*Entity 선언 후 JPA 활용*/
@Entity
@Table(name="member")   /*VO 습관때문에 붙여서 table 자동매핑이 안되기 때문에 선언*/
public class MemberVO {

//    @Id로 PK 선언
//    @GeneratedValue 로 DB에서 자동으로 pk 생성되는 것을 선언(아이덴티티 전략이라 함. GenerationType.IDENTITY)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column("name="username")     //컬럼이 다른 경우, 매핑시켜줄 때는 @Column
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
