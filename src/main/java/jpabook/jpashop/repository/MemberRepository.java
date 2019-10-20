package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // @PersistenceContext를 사용하는데 스프링부트에서 @Autowired로도 인젝션을 지원하므로
    // 생성자주입방식으로 사용할 수 있다. MemberService처럼 final + lombok(@RequiredArgsConstructor)사용
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // JPSQL의 문법은 SQL문과 다름. SQL은 테이블을 대상으로 한다면 JPSQL은 엔티티를 대상으로 한다. 여기서 m은 Memeber엔티티의 Alias이다.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
