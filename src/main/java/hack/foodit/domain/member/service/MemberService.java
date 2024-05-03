package hack.foodit.domain.member.service;

import hack.foodit.domain.member.entity.Member;
import hack.foodit.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member registerMember(Member member) {
        return memberRepository.save(member);
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
