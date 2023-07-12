package jpabook.jpashop;

import org.hibernate.boot.TempTableDdlTransactionHandling;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional // 이게 test에 있으면 테스트가 끝난 후 롤백함
    @Rollback(false) // 롤백 안 시키려면 false
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member); // 둘은 같다. 같은 트랜잭션 안에서 저장하고 조회하면 영속성 컨텍스트가 똑같다.
                                                     // 영속성 컨텍스트가 같으면 같은 id로 식별한다.

    }


}