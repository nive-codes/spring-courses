package hello.hello_artifact.service;

import hello.hello_artifact.domain.MemberVO;
import hello.hello_artifact.respository.MemberRepository;
import hello.hello_artifact.respository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*서비스*/
/*비지니스에 의존적으로 설계를 주로 하고 용어도 마찬가지.*/
/*테스트를 하고 싶을 때 command + shift + T를 할때 테스트가 만들어짐*/
//@Service  //springConfig.java에서 선언 되어 있음.
public class MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;

//    외부에서 memberRespository를 넣어주도록 변경
//    테스트 시 별도 생성이 아닌 같은 객체로 테스트

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }



    /*
    * 회원 가입
    * */

    public Long join(MemberVO memberVO) {

        /*command + option + v 하면 객체 자동 생성됨(Optional<MemberVO> result로 생성)*/
        /*중복회원 검증1 : 객체 생성*/
        Optional<MemberVO> result = memberRepository.findByUsername(memberVO.getName());

        /*Optional이라는 것으로 감싸줬으므로 ifPresent라는 것을 활용할 수 있다.*/
        /*null이 아니고 어떤 값이 있으면?*/
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

        /*Optional 을 바로 반환하는 것은 좋지 않음. 따라서 아래 코드로 변경 가능*/
        /*result 반환안해도 됨. 이미 조회 해온 값에서 처리*/
        /*중복회원 검증2 : 이미 객체가 있으니 바로 조건 처리*/
        /*findByUsername의 값은 Optional<MemberVO> 이므로 중복인지 체크 가능*/
        memberRepository.findByUsername(memberVO.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });


        /*컨트롤 + t -> 리팩토링 관련 단축키*/
        /*method 검색 후 생성 -> 이름 변경으로 처리*/
        /*중복회원 검증3 : 별도 메소드로 구현*/
        validateDupMember(memberVO);

        memberRepository.save(memberVO);
        return memberVO.getId();

    }

    private void validateDupMember(MemberVO memberVO) {
        memberRepository.findByUsername(memberVO.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    public List<MemberVO> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<MemberVO> findMemberById(Long id){
        return memberRepository.findById(id);
    }

}
