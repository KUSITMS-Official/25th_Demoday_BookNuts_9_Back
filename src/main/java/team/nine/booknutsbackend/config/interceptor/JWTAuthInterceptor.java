package team.nine.booknutsbackend.config.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import team.nine.booknutsbackend.config.JwtTokenProvider;
import team.nine.booknutsbackend.exception.user.UnAuthTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JWTAuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    //컨트롤러 실행 전 수행 (true -> 컨트롤러로 진입, false -> 진입 X)
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String token = jwtTokenProvider.resolveToken(request);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new UnAuthTokenException("유효하지 않은 X-AUTH-TOKEN");
        }

        return true;
    }

}