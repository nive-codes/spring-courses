package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.respository.MemberRepositoryOld;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepositoryOld memberRepository;
    @Autowired
    EntityManager em;

    @Test
//    @Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        //transaction이 commit이 될 때 db에 insert가 되므로 transaction 상태에서는 console log 확인이 어렵다. @Rollback(false)로 활용
        Long saveId = memberService.join(member);

        //then
//        em.flush(); //이렇게 하면 insert가 되고 transaction이 클래스에 있기 때문에 롤백이 됨.

        assertEquals(member, memberRepository.findOne(saveId)); //동일한 member인지?(영속성 객체, id가 같으면 같은걸로 인식)
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        try{
            memberService.join(member2);    //에러 발생해야함. -> 작성해둔 에러가 발생되어버림.
        } catch (IllegalStateException e){
            return; //정상적인 에러, 의도한 에러가 발생
        }
        //then
        fail("예외가 발생해야 한다.");   //의도하지 않은 에러이므로 오류.

    }

    //junit5를 활용한 assetThrows
    @Test
    public void 중복_회원_예외2() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);


        Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
    }



}