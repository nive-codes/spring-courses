package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryMemberRepository implements MemberRepository {

    /*메모리용 저장 객체 */
    /*동시성 이슈가 있을 수 있으므로 ConcurrentHashMap을 사용해야 함.*/
    private static Map<Long,Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);

    }

    @Override
    public Member findById(Long id) {
        return store.get(id);
    }
}
