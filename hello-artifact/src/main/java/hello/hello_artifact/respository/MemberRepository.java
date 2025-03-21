package hello.hello_artifact.respository;

import hello.hello_artifact.domain.MemberVO;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

/*레포지토리 - 저장소*/
/*상속받아서 사용할 interface를 정의*/
public interface MemberRepository {
    MemberVO save(MemberVO memberVO);
    Optional<MemberVO> findById(Long id);
    Optional<MemberVO> findByUsername(String username);
    List<MemberVO> findAll();
}
