package hoselabs.future_letter.global.jwt;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import hoselabs.future_letter.domain.user.entity.User;
import hoselabs.future_letter.global.security.MyUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String prefixJwt = request.getHeader(JwtProvider.ACCESS_TOKEN_HEADER);

        if(prefixJwt == null){
            chain.doFilter(request, response);
            return;
        }

        String jwt = prefixJwt.replace(JwtProvider.TOKEN_PREFIX, "");

        try {
            DecodedJWT decodedJWT = JwtProvider.verifyAccessToken(jwt);
            Long id = decodedJWT.getClaim("id").asLong();

            User user = User.builder().id(id).build();
            MyUserDetails myUserDetails = new MyUserDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    myUserDetails,
                    myUserDetails.getPassword(),
                    myUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (SignatureVerificationException sve) {
            log.error("액세스 토큰 검증 실패");

            JsonResponse jsonResponse = new JsonResponse("401", "J001", "액세스 토큰 검증 실패");
            String jsonString = jsonResponse.toJson();

            response.setStatus(401);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(jsonString);
        } catch (TokenExpiredException tee) {
            log.error("액세스 토큰 만료");

            JsonResponse jsonResponse = new JsonResponse("401", "J003", "액세스 토큰 만료");
            String jsonString = jsonResponse.toJson();

            response.setStatus(401);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(jsonString);
        }
    }
}
