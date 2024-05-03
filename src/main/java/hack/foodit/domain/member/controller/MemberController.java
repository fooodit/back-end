package hack.foodit.domain.member.controller;

import hack.foodit.domain.member.entity.Member;
import hack.foodit.domain.member.entity.dto.LoginDto;
import hack.foodit.domain.member.entity.dto.LoginResponse;
import hack.foodit.domain.member.entity.dto.SignUpDto;
import hack.foodit.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<Member> registerMember(@RequestBody SignUpDto signUpDto) {
        Member registeredMember = memberService.registerMember(signUpDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredMember);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        LoginResponse loginResponse = memberService.login(loginDto);
        if (loginResponse != null) {
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login fail");
        }
    }
}
