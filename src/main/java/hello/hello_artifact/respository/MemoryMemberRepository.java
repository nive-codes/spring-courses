package hello.hello_artifact.respository;

import hello.hello_artifact.domain.MemberVO;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/*리포지토리를 상속받은 구현체*/
/*도메인 객체를 저장하고 관리*/
public class MemoryMemberRepository implements MemberRepository {

//    private static ConcurrentMap <Long,MemberVO> store = new ConcurrentHashMap<>();   //동시성 해결
    private static Map <Long,MemberVO> store = new HashMap<>();

//    private static AtomicLong sequnce = new AtomicLong();   //동시성 해결
    private static long sequnce = 0L;
    @Override
    public MemberVO save(MemberVO memberVO) {
        memberVO.setId(++sequnce);
        store.put(memberVO.getId(),memberVO);
        return memberVO;
    }

    @Override
    public Optional<MemberVO> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<MemberVO> findByUsername(String username) {
        /*람다 활용 filter를 통해 이름이 같은걸 찾아서 return*/
        return store.values().stream()
                .filter(memberVO -> memberVO.getName().equals(username)).findAny();
    }

    @Override
    public List<MemberVO> findAll() {
        /*멤버들을 다 반환한다.*/
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
