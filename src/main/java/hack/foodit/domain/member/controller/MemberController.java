package hack.foodit.domain.member.controller;

import hack.foodit.domain.member.entity.Member;
import hack.foodit.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/register")
    public ResponseEntity<Member> registerMember(@RequestBody Member member) {
        Member registeredMember = memberRepository.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredMember);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Member> getMemberByEmail(@RequestParam String email) {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
