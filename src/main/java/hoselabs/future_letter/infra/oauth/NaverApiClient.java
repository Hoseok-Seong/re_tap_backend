package hoselabs.future_letter.infra.oauth;

import hoselabs.future_letter.domain.auth.dto.NaverUserInfo;
import hoselabs.future_letter.domain.auth.dto.OauthUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverApiClient {

    private final RestTemplate restTemplate;

    private static final String NAVER_USER_INFO_URL = "https://openapi.naver.com/v1/nid/me";

    public OauthUserInfo getUserInfo(final String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                NAVER_USER_INFO_URL,
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map<String, Object> body = response.getBody();

        log.info("naver user info = {}", body);

        Map<String, Object> account = (Map<String, Object>) body.get("account");

        return new NaverUserInfo(
                (String) account.get("email"),
                null
        );
    }
}
