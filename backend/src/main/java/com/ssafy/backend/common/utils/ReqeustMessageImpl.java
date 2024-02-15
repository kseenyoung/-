package com.ssafy.backend.common.utils;

import com.ssafy.backend.user.model.dto.OpenviduRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
public class ReqeustMessageImpl implements RequestMessage{
    @Value("${openvidu.url}")
    private String OPENVIDU_URL;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    @Override
    public void RequestOpenviduMessage(OpenviduRequestDTO dto) {
        // 로그인 성공시 친구들에게 시그널 전송
//        log.info("RequestOpenviduMessage dto로 메세지 보내기 -> {}", dto);
        URI uri = UriComponentsBuilder
                .fromUriString(OPENVIDU_URL)
                .path("/openvidu/api/signal")
                .encode()
                .build()
                .toUri();

        RequestEntity<String> requestEntity = RequestEntity
                .post(uri)
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic T1BFTlZJRFVBUFA6TVlfU0VDUkVU")
                .body(dto.toJson());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        try {
           restTemplate.postForEntity(uri, requestEntity, Object.class);
        } catch (Exception e) {

        }

    }
}
