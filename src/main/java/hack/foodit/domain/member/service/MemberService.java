package hack.foodit.domain.member.service;

import hack.foodit.domain.member.entity.Member;
import hack.foodit.domain.member.entity.dto.LoginDto;
import hack.foodit.domain.member.entity.dto.LoginResponse;
import hack.foodit.domain.member.entity.dto.SignUpDto;
import hack.foodit.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member registerMember(SignUpDto signUpDto) {
        return memberRepository.save(signUpDto.toEntity());
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public LoginResponse login(LoginDto loginDto) {
        Optional<Member> member = Optional.ofNullable(memberRepository.findByEmail(
            loginDto.getEmail()));
        if (member.isPresent() && member.get().getPassword().equals(loginDto.getPassword())) {

            return LoginResponse.builder()
                .memberId(member.get().getId())
                .build();
        } else {
            return null;
        }
    }
}
