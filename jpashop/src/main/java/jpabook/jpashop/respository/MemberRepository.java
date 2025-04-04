package jpabook.jpashop.respository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hosikchoi
 * @class MemberRepository
 * @desc spring data jpa가 적용된 memberRepository
 * @since 2025-04-04
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
}
