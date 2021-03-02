package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepositoryOld memberRepositoryOld;
    @Test
    void 회원가입() throws Exception{
        Member member = new Member();
        member.setName("kim");

        Long savedId = memberService.join(member);

        Assertions.assertThat(member.getId()).isEqualTo(savedId);
    }


    @Test
    void 중복() throws Exception{
        Member member = new Member();
        member.setName("kim");

        Long savedId = memberService.join(member);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 등록됌.");
    }
}