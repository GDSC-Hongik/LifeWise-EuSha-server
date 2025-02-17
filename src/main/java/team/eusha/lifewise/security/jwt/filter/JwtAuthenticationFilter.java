package team.eusha.lifewise.security.jwt.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import team.eusha.lifewise.security.jwt.exception.JwtExceptionCode;
import team.eusha.lifewise.security.jwt.token.JwtAuthenticationToken;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        AntPathMatcher pathMatcher = new AntPathMatcher();

        String[] categoryPatterns = {
                "/categories",
                "/categories/*/subcategories",
                "/categories/*/subcategories/all",
                "/categories/*/subcategories/*"
        };

        boolean shouldNotFilter = Arrays.stream(categoryPatterns)
                .anyMatch(pattern -> {
                    boolean matched = pathMatcher.match(pattern, path);
                    return matched;
                });

        return shouldNotFilter || Arrays.asList("/", "/members/signup", "/members/login", "/members/refreshToken").contains(path);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = "";
        try {
            token = getToken(request);
            log.info("추출된 토큰 값:[{}]", token);
            log.info("Token length: {}", (token != null ? token.length() : "null"));
            log.info("StringUtils.hasText result: {}", StringUtils.hasText(token));
            if (StringUtils.hasText(token)) {
                log.info("토큰 유효성 통과, 인증을 위한 과정 진행중");
                getAuthentication(token);
            } else {
                log.info("토큰 유효성 실패");
            }
            filterChain.doFilter(request, response);
        } catch (NullPointerException | IllegalStateException e) {
            request.setAttribute("exception", JwtExceptionCode.NOT_FOUND_TOKEN.getCode());
            log.error("Not found Token // token : {}", token);
            log.error("Set Request Exception Code : {}", request.getAttribute("exception"));
            throw new BadCredentialsException("throw new not found token exception");
        } catch (SecurityException | MalformedJwtException e) {
            request.setAttribute("exception", JwtExceptionCode.INVALID_TOKEN.getCode());
            log.error("Invalid Token // token : {}", token);
            log.error("Set Request Exception Code : {}", request.getAttribute("exception"));
            throw new BadCredentialsException("throw new invalid token exception");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", JwtExceptionCode.EXPIRED_TOKEN.getCode());
            log.error("EXPIRED Token // token : {}", token);
            log.error("Set Request Exception Code : {}", request.getAttribute("exception"));
            throw new BadCredentialsException("throw new expired token exception");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", JwtExceptionCode.UNSUPPORTED_TOKEN.getCode());
            log.error("Unsupported Token // token : {}", token);
            log.error("Set Request Exception Code : {}", request.getAttribute("exception"));
            throw new BadCredentialsException("throw new unsupported token exception");
        } catch (Exception e) {
            log.error("====================================================");
            log.error("JwtFilter - doFilterInternal() 오류 발생");
            log.error("token : {}", token);
            log.error("Exception Message : {}", e.getMessage());
            log.error("Exception StackTrace : {");
            e.printStackTrace();
            log.error("}");
            log.error("====================================================");
            throw new BadCredentialsException("throw new exception");
        }
    }

    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer")) {
            String[] arr = authorization.split(" ");
            return arr[1];
        }
        return null;
    }

    private void getAuthentication(String token) {
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(token);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
    }


}
