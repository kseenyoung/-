package com.ssafy.backend.room.service;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.room.model.domain.Answer;
import com.ssafy.backend.room.model.dto.AnswerDto;
import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.room.model.dto.RoomEnterDto;
import com.ssafy.backend.room.model.repository.AnswerRepository;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    @Value("${openvidu.url}")
    private String OPENVIDU_URL;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    private final AnswerRepository answerRepository;

    private OpenVidu openvidu;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    @Override
    public String enterRandomroom(RoomEnterDto roomEnterDto) throws Exception {
        String sessionName = roomEnterDto.getSessionName();
        Session session;

        // 과목당 3개방(20명씩)
        String[] rooms = new String[]{sessionName+"_1", sessionName+"_2", sessionName+"_3"};

        // 세션에 해당하는 방이 존재하는지 확인
        sessionName = getRandomroom(sessionName);
        roomEnterDto.setSessionName(sessionName);
        session = openvidu.getActiveSession(sessionName);

        if(session == null){
            // 방이 존재하지 않다면 생성하라
            HashMap<String,String> SessionPropertyJson = roomEnterDto.toSessionPropertyJson();
            SessionProperties properties = SessionProperties.fromJson(SessionPropertyJson).build();
            session = openvidu.createSession(properties);
        } else if (session.getActiveConnections().size() > 20) {
        }

        ConnectionProperties properties = new ConnectionProperties.Builder().build();
        Connection connection = session.createConnection(properties);
        return connection.getToken();
    }

    @Override
    public String enterMoccojiroom(RoomEnterDto roomEnterDto) throws Exception {
        String SessionName = roomEnterDto.getSessionName();
        Session session;
        session = openvidu.getActiveSession(SessionName);

        if(session == null){
            // 길드방이 존재하지 않다면 생성하라 (길드방에 아무도 없어서 세션이 종료된 상태라면 생성하라)
            HashMap<String,String> SessionPropertyJson = roomEnterDto.toSessionPropertyJson();
            SessionProperties properties = SessionProperties.fromJson(SessionPropertyJson).build();
            session = openvidu.createSession(properties);
        }

        ConnectionProperties properties = new ConnectionProperties.Builder().build();
        Connection connection = session.createConnection(properties);

        return connection.getToken();
    }

    @Override
    public void askQuestion(QuestionDto questionDto) throws Exception {
        String sessionName = questionDto.getSessionName();
        Session session;
        session = openvidu.getActiveSession(sessionName);
        if(session == null){
            throw new MyException("존재하지 않는 세션입니다", HttpStatus.NOT_FOUND);
        }

        HttpPost request = new HttpPost(OPENVIDU_URL + "openvidu/api/signal");
        String secret = "Basic "+OPENVIDU_SECRET;
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Authorization", secret);
        StringEntity entity = new StringEntity(questionDto.toJsonString(),StandardCharsets.UTF_8);
        request.setEntity(entity);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpResponse response = httpClient.execute(request);
        } catch (Exception e){
            throw new MyException("Openvidu서버 통신에 실패했습니다.",HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void answerQuestion(AnswerDto answerDto) throws Exception {
        String sessionName = answerDto.getSessionName();
        Session session;
        session = openvidu.getActiveSession(sessionName);
        if(session == null){
            throw new MyException("존재하지 않는 세션입니다", HttpStatus.NOT_FOUND);
        }

        // DB에 대답 저장
        Answer answer = saveAnswer(answerDto);
        answerDto.setAnswerId(answer.getAnswerId());

        // 답변 문제번호 전송
        HttpPost request = new HttpPost(OPENVIDU_URL + "openvidu/api/signal");
        String secret = "Basic "+OPENVIDU_SECRET;
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Authorization", secret);
        StringEntity entity = new StringEntity(answerDto.toJsonString(),StandardCharsets.UTF_8);
        request.setEntity(entity);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpResponse response = httpClient.execute(request);
            System.out.println("통신에 성공했습니다.");
        } catch (Exception e){
            System.out.println(e);
            throw new MyException("Openvidu서버 통신에 실패했습니다.",HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Answer saveAnswer(AnswerDto answerDto) throws Exception {
        Answer answer = answerDto.toEntity();
        answer = answerRepository.save(answer);
        return answer;
    }

    @Override
    public List<AnswerDto> findAnswerByQuestionId(int questionId) throws Exception {
        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);
        List<AnswerDto> answerDtos = answers.stream()
                .map(a -> new AnswerDto(a.getAnswerId(),a.getSessionName(),a.getAnswer(),a.getQuestionId()))
                .collect(Collectors.toList());
        return answerDtos;
    }

    public String getRandomroom(String sessionName){
        Random random = new Random();
        int roomNumber = random.nextInt( 3) + 1; // 1-3
        return sessionName+roomNumber;
    }

    public String encodeBase64(){
        String basicSecret = "Basic "+OPENVIDU_SECRET;
        byte[] encodedBytes = Base64.getEncoder().encode(basicSecret.getBytes());
        return new String(encodedBytes);
    }
}
