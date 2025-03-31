package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.respository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hosikchoi
 * @class MemberService
 * @desc Member의 비지니스 서비스
 * @since 2025-03-24
 */

@Service
@Transactional(readOnly = true)  //기본적으로 데이터 변경은 꼭 트랜잭션 안에서 활용되어야 한다. 클래스에서 퍼블릭메서드들은 기본적으로 처리가 됨
//@AllArgsConstructor
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

//    private MemberRepository memberRepository;
//    private final MemberRepository memberRepository;    //생성자일땐 final을 권장 값을 꼭 있어야되고 세팅이 꼭 되어 있어야되는 것. 컴파일 시점에서 에러 확인 가능
//
//    @Autowired //생성자 주입 방식 Autowired을 뺄 수 있음.
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     * @param member
     * @return
     */
    @Transactional //서비스에 이미 readonly가 있어도 메서드에 따로 걸어둔거면 메서드의 Transaction이 실행이 됨.
    public Long join(Member member) {
        validateDuplicateMember(member);    //중복 회원 검증
        memberRepository.save(member);
        return member.getId();  //db에 들어가지 않아도 이미 값은 세팅이 되어 있다
    }

    /**
     * 중복회원 검증 로직
     * @param member
     */
    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");  //이렇게 하더라도, 유니크 제약조건으로 db에서 걸던지, 혹은 db에서 select해오던지.
        }
    }

    //회원 전체 조회
//    @Transactional(readOnly = true) //읽기전용 트랜잭션이면 단순한 읽기용 모드이므로 상대적으로 최적화
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //단건 조회
//    @Transactional(readOnly = true) //읽기전용 트랜잭션이면 단순한 읽기용 모드이므로 상대적으로 최적화
    public Member findMember(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
        //변경 감지를 통해 트랜잭션 aop가 끝나는 시점 commit되면서 flush, 영속성 컨텍스트 전부  처리됨.

    }
}
