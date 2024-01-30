package com.ssafy.backend.user.service;


import com.ssafy.backend.common.exception.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import static com.ssafy.backend.common.response.BaseResponseStatus.FAIL_TO_CONNECT;


@Service
public class ReCaptchaService {

    @Value("${google.recaptcha.key}")
    private static String googlekey;

    public static boolean isRobot(String recaptchaResponse) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL("https://www.google.com/recaptcha/api/siteverify")
                    .openConnection();
            String params = "secret=" + googlekey + "&response=" + recaptchaResponse;
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();

            // 결과코드 확인(200 : 성공)
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                return true;
            } else { // 결과코드 실패.
                return false;
            }

        } catch (Exception e){
            throw new BaseException(FAIL_TO_CONNECT);
        }

    }

}
