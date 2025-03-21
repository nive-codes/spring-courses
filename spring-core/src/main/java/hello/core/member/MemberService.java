package hello.core.member;

/*회원 가입 및 조회가 있어야 함.*/
public interface MemberService {

    void join(Member member);

    Member findMember(Long memberId);
}
