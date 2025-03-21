package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*끝에 impl 붙이는 이유는 관례상 구현체가 하나인 경우 impl을 붙임*/
@Component
public class MemberServiceImpl implements MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;

    /*생성자에 AutoWired를 붙여주면 타입에 맞는 애를 spring이 찾아서 자동으로 연결해서 주입시켜줌.*/
    @Autowired  //ac.getBean(MemberRepository.class)
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }


    /*싱글톤 테스트 용도*/
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
