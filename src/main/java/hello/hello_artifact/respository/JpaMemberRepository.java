package hello.hello_artifact.respository;

import hello.hello_artifact.domain.MemberVO;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;



public class JpaMemberRepository implements MemberRepository {

    /*jap는 EntityManager라는걸로 모두 동작이 이루어짐*/
    /*spring이 생성하고 자동으로 DB연결을 해줌*/
    /*만들어진걸 주입받아서 생성하면 됨*/
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public MemberVO save(MemberVO memberVO) {
//        persist : 영구 저장하다(insert), return이 없음, setId까지 다 해줌
        em.persist(memberVO);
        return memberVO;
    }

    @Override
    public Optional<MemberVO> findById(Long id) {
        MemberVO memberVO = em.find(MemberVO.class,id);
        return Optional.ofNullable(memberVO);
    }

    /*PK가 아닌걸로 검색을 할 시에는 JPQL을 작성해야함*/
    @Override
    public Optional<MemberVO> findByUsername(String username) {
//        pk는 findById와 동일하게 쓰면 되지만 username같은 경우에는 JAP Query Language라는 객체지향 쿼리를 사용
        List resultList = em.createQuery("select m from MemberVO m where m.name = :username",MemberVO.class)
                .setParameter("username", username) /*:username 파라미터에 username set*/
                .getResultList();   /*검색된 결과*/

        /*optional은 stream으로 반복한 후 객체를 전부 return 처리... 귀찮음*/
        return resultList.stream().findAny();

    }
    /*PK가 아닌걸로 검색을 할 시에는 JPQL을 작성해야함*/
    @Override
    public List<MemberVO> findAll() {
//        pk는 findById와 동일하게 쓰면 되지만 username같은 경우에는 JAP Query Language라는 객체지향 쿼리를 사용
//        List<MemberVO> resultList = em.createQuery("SELECT m from member m",MemberVO.class).getResultList();
//        return resultList;

//        return에 command + option + n 단축키 실행 시 객체 생성을 하지 않고 inline으로 return 처리
        return em.createQuery("SELECT m from MemberVO m",MemberVO.class).getResultList();

//        엔티티를 대상으로 쿼리를 날림. sql이면 * 이나 컬럼을 적지만 얘는 엔티티 자체를 select하고 끝



    }
}
