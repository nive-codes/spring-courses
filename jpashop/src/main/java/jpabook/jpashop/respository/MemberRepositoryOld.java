package jpabook.jpashop.respository;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hosikchoi
 * @class MemberRepository
 * @desc 회원 관리에 쓰이는 repository
 * @since 2025-03-24
 */

@Repository
@RequiredArgsConstructor //Spring data jpa를 쓰면 얘도 생성자 주입방식을 이 애노테이션을 통해 사용할 수 있다.
public class MemberRepositoryOld {

    private final EntityManager em;

    /*Spring이 entityManager를 만들어서 주입해줌*/
//    @PersistenceContext
//    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); //type과 pk를 넣어주면 됨.
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();  //JQPL : sql과 비슷함. 대신 from이 table이 아니라 entity 객체가 됨.
    }

    public List<Member> findByName(String name) {    //특정 이름에 해당하는 list만
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name",name).getResultList();
    }
}
