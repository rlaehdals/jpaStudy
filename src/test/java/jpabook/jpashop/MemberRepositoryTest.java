package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepositoryOld memberRepositoryOld;

    @Test
    void test(){
        Member member = new Member();
        Long saveId = memberRepositoryOld.save(member);

        Member member1 = memberRepositoryOld.findOne(saveId);

        Assertions.assertThat(member).isEqualTo(member1);
    }
}