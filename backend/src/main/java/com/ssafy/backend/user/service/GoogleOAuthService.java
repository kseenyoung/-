package com.ssafy.backend.user.service;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.user.model.vo.GoogleOAuthRequest;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static com.ssafy.backend.common.response.BaseResponseStatus.FAIL_LOGIN;

@Service
public class GoogleOAuthService {

    public String getGoogleAccessToken(String authCode) {
        RestTemplate restTemplate = new RestTemplate();

        GoogleOAuthRequest googleOAuthRequestParam = GoogleOAuthRequest
                .builder()
                .clientId("273219571369-bdo0hmfdde3j8olh6i5j20ln6iulph9h.apps.googleusercontent.com")
                .clientSecret("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")
                .code(authCode)
                .redirectUri("https://localhost:8080/dagak/user/googleOauth")
                .grantType("authorization_code")
                .build();

        ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity("https://oauth2.googleapis.com" + "/token", googleOAuthRequestParam, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        //   id_token은 jwt 형식
        String jwtToken = (String) responseBody.get("id_token");
        String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com" + "/tokeninfo").queryParam("id_token", jwtToken).toUriString();

        JSONObject resultJson = restTemplate.getForObject(requestUrl, JSONObject.class);

        // 구글 정보조회 성공
        if (resultJson != null) {
            String googleEmail = (String) resultJson.get("email");
            return googleEmail;

            // 구글 정보조회 실패
        } else {
            throw new BaseException(FAIL_LOGIN);
        }
    }

}
