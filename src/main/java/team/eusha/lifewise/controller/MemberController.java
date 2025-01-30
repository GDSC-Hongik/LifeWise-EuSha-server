package team.eusha.lifewise.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import team.eusha.lifewise.domain.Member;
import team.eusha.lifewise.dto.request.MemberLoginRequest;
import team.eusha.lifewise.dto.request.MemberSignupRequest;
import team.eusha.lifewise.dto.response.MemberLoginResponse;
import team.eusha.lifewise.dto.response.MemberSignupResponse;
import team.eusha.lifewise.security.jwt.util.JwtTokenizer;
import team.eusha.lifewise.service.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid MemberSignupRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Member member = new Member();
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword()));

        Member saveMember = memberService.addMember(member);

        MemberSignupResponse response = new MemberSignupResponse();
        response.setMemberId(saveMember.getMemberId());
        response.setName(saveMember.getName());
        response.setCreatedAt(saveMember.getCreatedAt());
        response.setEmail(saveMember.getEmail());

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid MemberLoginRequest login) {

        Long memberId = 1L;
        String email = login.getEmail();
        List<String> roles = List.of("USER");

        // JWT 토큰 생성
        String accessToken = jwtTokenizer.createAccessToken(memberId, email, roles);
        String refreshToken = jwtTokenizer.createRefreshToken(memberId, email, roles);

        MemberLoginResponse loginResponse = MemberLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(memberId)
                .name("name")
                .build();
        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        // token repository에서 refresh Token에 해당하는 값을 삭제
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
