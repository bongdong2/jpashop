package jpabook.jpashop.service;

import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 성능최적화 된다. 조회시에만 쓴다.
@RequiredArgsConstructor // final 있는 필드만 가지고 생성자를 만든다.
public class MemberService {

    // contstruct injection
    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional // 기본은 readOnly = false이다.
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 멤버수를 구해서 0 이상이면 예외를 던지는 게 더 최적화 되는데 여기서는 그냥 진행한다.
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw  new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
