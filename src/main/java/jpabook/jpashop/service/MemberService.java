package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepositoryOld memberRepositoryOld;
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepositoryOld.findOne((id));
        member.setName(name);
    }

    @Transactional
    public Long join(Member member){

        validateDuplicateMember(member);
        memberRepositoryOld.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepositoryOld.findByName(member.getName()).stream().findAny()
                .ifPresent(m->{
                    throw new IllegalStateException("이미 등록됌.");
                });
    }
    public List<Member> findMembers(){
        return memberRepositoryOld.findAll();
    }

    public Member findOne(Long id){
        return memberRepositoryOld.findOne(id);
    }

}
