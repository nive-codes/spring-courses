package hello.hello_artifact.respository;

import hello.hello_artifact.domain.MemberVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*inetface가 interface를 받을때는 extands (상속,확장)*/
                                                    /*<도메인과 PK>*/
public interface SpringDataJpaMemberRepository extends JpaRepository<MemberVO,Long>,MemberRepository {

    /*기본 crud를 지원해주기 때문에 나머지들만 interface로 처리를 하거나, 어려운 쿼리가 필요한 경우 queryDSL, 혹은 java template, mybatis를 사용하기도 한다.*/
//    JPQL select m from Member m where m.id = id
    @Override
    Optional<MemberVO> findById(Long id);

    /*혹은 리플렉션 기술로 뒤에 더 붙인다던지 이런식으로 AndName 이런식으로 붙여서 조화할 수 있도록*/
//    Optional<MemberVO> findByEmailAndName(String email, String name);


    @Override
    Optional<MemberVO> findByUsername(String name);
}
