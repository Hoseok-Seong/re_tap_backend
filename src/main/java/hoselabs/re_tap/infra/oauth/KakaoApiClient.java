package hoselabs.re_tap.infra.oauth;

import hoselabs.re_tap.domain.auth.dto.KakaoUserInfo;
import hoselabs.re_tap.domain.auth.dto.OauthUserInfo;
import hoselabs.re_tap.infra.exception.KakaoApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoApiClient {

    private final RestTemplate restTemplate;

    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    public OauthUserInfo getUserInfo(final String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    KAKAO_USER_INFO_URL,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            Map<String, Object> body = response.getBody();

            log.info("kakao user info = {}", body);

            Map<String, Object> properties = (Map<String, Object>) body.get("properties");

            return new KakaoUserInfo(
                    String.valueOf(body.get("id")),
                    properties != null ? (String) properties.get("profile_image") : null
            );
        } catch (HttpClientErrorException e) {
            throw new KakaoApiException("카카오 로그인 Api 에러: " + e.getMessage());
        }
    }
}
