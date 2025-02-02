package team.eusha.lifewise.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import team.eusha.lifewise.domain.Member;
import team.eusha.lifewise.domain.Role;
import team.eusha.lifewise.dto.request.MemberLoginRequest;
import team.eusha.lifewise.dto.request.MemberSignupRequest;
import team.eusha.lifewise.dto.response.MemberLoginResponse;
import team.eusha.lifewise.dto.response.MemberSignupResponse;
import team.eusha.lifewise.security.jwt.util.JwtTokenizer;
import team.eusha.lifewise.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

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
        member.setMemberName(request.getMemberName());
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword()));

        Member saveMember = memberService.addMember(member);

        MemberSignupResponse response = new MemberSignupResponse();
        response.setMemberId(saveMember.getMemberId());
        response.setMemberName(saveMember.getMemberName());
        response.setCreatedAt(saveMember.getCreatedAt());
        response.setEmail(saveMember.getEmail());

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid MemberLoginRequest login, BindingResult bindingResult) {

       if(bindingResult.hasErrors()) {
           return new ResponseEntity(HttpStatus.BAD_REQUEST);
       }

       Member member = memberService.findByEmail(login.getEmail());
       if(!passwordEncoder.matches(login.getPassword(), member.getPassword())) {
           return new ResponseEntity(HttpStatus.UNAUTHORIZED);
       }

       List<String> roles = member.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        // JWT 토큰 생성
        String accessToken = jwtTokenizer.createAccessToken(member.getMemberId(),member.getEmail(),member.getMemberName(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getMemberId(),member.getEmail(),member.getMemberName(), roles);

        MemberLoginResponse loginResponse = MemberLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(member.getMemberId())
                .memberName(member.getMemberName())
                .build();
        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
