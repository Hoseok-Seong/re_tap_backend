package hoselabs.re_tap.infra.oauth;

import hoselabs.re_tap.domain.auth.dto.GoogleUserInfo;
import hoselabs.re_tap.domain.auth.dto.OauthUserInfo;
import hoselabs.re_tap.infra.exception.GoogleApiException;
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
public class GoogleApiClient {

    private final RestTemplate restTemplate;

    private static final String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    public OauthUserInfo getUserInfo(final String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    GOOGLE_USER_INFO_URL,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            Map<String, Object> body = response.getBody();

            log.info("google user info = {}", body);

            return new GoogleUserInfo(
                    (String) body.get("email"),
                    null
            );
        } catch (HttpClientErrorException e) {
            throw new GoogleApiException("구글 로그인 Api 에러: " + e.getMessage());
        }
    }
}
