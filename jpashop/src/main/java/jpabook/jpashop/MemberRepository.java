package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

//DAO와 비슷한 역할
//Component 스캔의 대상으로, 자동으로 Spring Bean에 등록이 됩니다.
//엔티티같은걸 찾아주는 역할
@Repository
public class MemberRepository {

    @PersistenceContext //모두 spring container에 올라가서 사용되는 code이므로, entity manager를 주입을 해줌
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
