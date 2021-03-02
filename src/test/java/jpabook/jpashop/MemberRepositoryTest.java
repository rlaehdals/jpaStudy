package jpabook.jpashop;

import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void test(){
        Member member = new Member();
        Long saveId = memberRepository.save(member);

        Member member1 = memberRepository.findOne(saveId);

        Assertions.assertThat(member).isEqualTo(member1);
    }
}