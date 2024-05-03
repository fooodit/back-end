package hack.foodit.service;

import hack.foodit.domain.member.Member;
import hack.foodit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member registerMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public boolean login(String email, String password) {
        Optional<Member> member = Optional.ofNullable(memberRepository.findByEmail(email));
        return member.isPresent() && member.get().getPassword().equals(password);
    }
}
