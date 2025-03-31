package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hosikchoi
 * @class MemberApiController
 * @desc api용 member controller
 * @since 2025-03-31
 */
//@Controller
//@ResponseBody
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){    //json으로 온 데이터를 member에 매핑해줌
//        근데 entity말고 dto를 받아서 처리하도록 -> api 스펙이 바뀜 -> api 스펙으로 dto를 생성해서 받고, entity 변경되도 api 스펙은 문제없도록 처리.(외부노출금지)
//        entity를 꼭 외부 노출하지 말고, 파라미터로도 받지 말아라! -> v2처럼 할 것. request를 받아서 entity가 변경되도록 문제 없도록.

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }


    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest {
        private String name;


    }


    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
