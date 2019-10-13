package jpabook.jpashop.Service;

import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    //@Rollback(false)
    //insert쿼리가 보고 싶으면
    public void 회원가입() throws Exception {
        //given 테스트 전의 상태
        Member member = new Member();
        member.setName("jwa");

        //when 테스트 행위
        Long saveId = memberService.join(member);

        //than 테스트 검증
        //em.flush(); // insert쿼리가 보고 싶으면
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_개발() throws Exception {
        //given 테스트 전의 상태
        Member member1 = new Member();
        member1.setName("jwa");

        Member member2 = new Member();
        member2.setName("jwa");

        //when 테스트 행위
        memberService.join(member1);
        memberService.join(member2);  // 예외가 발생해야 한다.

        //than 테스트 검증
        fail("예외가 발생해야 한다.");
    }
}