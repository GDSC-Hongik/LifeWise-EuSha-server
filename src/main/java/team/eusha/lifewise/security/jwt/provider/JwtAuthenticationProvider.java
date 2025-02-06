package team.eusha.lifewise.security.jwt.provider;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import team.eusha.lifewise.security.jwt.token.JwtAuthenticationToken;
import team.eusha.lifewise.security.jwt.util.JwtTokenizer;
import team.eusha.lifewise.security.jwt.util.LoginInfoDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenizer jwtTokenizer;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        Claims claims = jwtTokenizer.parseAccessToken(authenticationToken.getToken());

        String email = claims.getSubject();

        LoginInfoDto loginInfoDto = new LoginInfoDto();
        loginInfoDto.setMemberId(Long.valueOf((Integer) claims.get("memberId")));
        loginInfoDto.setEmail(claims.getSubject());
        loginInfoDto.setMemberName((String) claims.get("memberName"));
        List<GrantedAuthority> authorities = getGrantedAuthorities(claims);

        return new JwtAuthenticationToken(authorities, email, null);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Claims claims) {
        List<String> roles = (List<String>) claims.get("roles");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(()-> role);
        }
        return authorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
