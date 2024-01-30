package com.ssafy.backend.room.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.room.model.domain.Answer;
import com.ssafy.backend.room.model.domain.Question;
import com.ssafy.backend.room.model.dto.AnswerDto;
import com.ssafy.backend.room.model.dto.ConnectionDto;
import com.ssafy.backend.room.model.dto.QuestionDto;
import com.ssafy.backend.room.model.dto.RoomEnterDto;
import com.ssafy.backend.room.model.repository.AnswerRepository;
import com.ssafy.backend.room.model.repository.QuestionRepository;
import com.ssafy.backend.user.model.dto.OpenviduRequestDto;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
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
    private final QuestionRepository questionRepository;

    private OpenVidu openvidu;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    @Override
    public String enterDefaultroom(RoomEnterDto roomEnterDto) throws Exception {
        Session session = openvidu.getActiveSession("default");
        if(session == null){
            // 방이 존재하지 않다면 생성하라
            HashMap<String,String> SessionPropertyJson = roomEnterDto.toSessionPropertyJson();
            SessionProperties properties = SessionProperties.fromJson(SessionPropertyJson).build();
            session = openvidu.createSession(properties);
        } else if (session.getActiveConnections().size() > 20) {
            // 방에 20명이상이 있다면
        }
        ConnectionProperties properties = new ConnectionProperties.Builder().build();
        Connection connection = session.createConnection(properties);
        return connection.getToken();
    }

    @Override
    public ConnectionDto enterRandomroom(RoomEnterDto roomEnterDto) throws Exception {
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
            // 방에 20명이상이 있다면
        }

        ConnectionProperties properties = new ConnectionProperties.Builder().build();
        Connection connection = session.createConnection(properties);
        ConnectionDto connectionDto = new ConnectionDto(sessionName,connection.getToken());
        return connectionDto;
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
    public QuestionDto askQuestion(QuestionDto questionDto) throws Exception {
        String sessionId = questionDto.getSession();
        Session session;
        session = openvidu.getActiveSession(sessionId);
        if(session == null){
//            throw new MyException("존재하지 않는 세션입니다", HttpStatus.NOT_FOUND);
        }

        // DB에 질문 저장
        Question question = saveQuestion(questionDto);
        String questionId = question.getQuestionId();
        questionDto.setQuestionId(questionId);

        ObjectMapper om = new ObjectMapper();
        OpenviduRequestDto openviduRequestDto = new OpenviduRequestDto(sessionId,"question",om.writeValueAsString(questionDto));
        URI uri = UriComponentsBuilder
                .fromUriString(OPENVIDU_URL)
                .path("/openvidu/api/signal")
                .encode()
                .build()
                .toUri();

        String secret = "Basic " + OPENVIDU_SECRET;
        secret = Base64.getEncoder().encodeToString(secret.getBytes());

        RequestEntity<String> requestEntity = RequestEntity
                .post(uri)
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic T1BFTlZJRFVBUFA6TVlfU0VDUkVU")
                .body(openviduRequestDto.toJson());

        System.out.println(openviduRequestDto.toJson());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<QuestionDto> responseEntity = restTemplate.postForEntity(uri, requestEntity, QuestionDto.class);
        return questionDto;
    }

    @Override
    public AnswerDto answerQuestion(AnswerDto answerDto) throws Exception {
        String sessionId = answerDto.getSession();
        Session session;
        System.out.println("sessionId"+sessionId);
        session = openvidu.getActiveSession(sessionId);
        if(session == null){
//            throw new MyException("존재하지 않는 세션입니다", HttpStatus.NOT_FOUND);
        }

        // DB에 대답 저장
        Answer answer = saveAnswer(answerDto);
        String answerId = answer.getAnswerId();
        answerDto.setAnswerId(answerId);
        ObjectMapper om = new ObjectMapper();

        // 답변 문제번호 전송
        OpenviduRequestDto openviduRequestDto = new OpenviduRequestDto(sessionId,"answer",om.writeValueAsString(answerDto));
        URI uri = UriComponentsBuilder
                .fromUriString(OPENVIDU_URL)
                .path("/openvidu/api/signal")
                .encode()
                .build()
                .toUri();

        String secret = "Basic " + OPENVIDU_SECRET;
        secret = Base64.getEncoder().encodeToString(secret.getBytes());

        RequestEntity<String> requestEntity = RequestEntity
                .post(uri)
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic T1BFTlZJRFVBUFA6TVlfU0VDUkVU")
                .body(openviduRequestDto.toJson());

        System.out.println(openviduRequestDto.toJson());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<QuestionDto> responseEntity = restTemplate.postForEntity(uri, requestEntity, QuestionDto.class);

        return answerDto;
    }

    @Override
    public Answer saveAnswer(AnswerDto answerDto) throws Exception {
        Answer answer = answerDto.toEntity();
        answer = answerRepository.save(answer);
        return answer;
    }

    @Override
    public Question saveQuestion(QuestionDto questionDto) throws Exception {
        Question question = questionDto.toEntity();
        question = questionRepository.save(question);
        System.out.println(question);
        return question;
    }


    @Override
    public List<AnswerDto> findAnswerByQuestionId(String questionId) throws Exception {
        List<Answer> answers = answerRepository.findByQuestionId(questionId);
        List<AnswerDto> answerDtos = answers.stream()
                .map(a -> new AnswerDto(a.getUserId(),a.getSession(),a.getAnswer(),a.getQuestionId()))
                .collect(Collectors.toList());
        return answerDtos;
    }

    public String getRandomroom(String sessionName){
        Random random = new Random();
        int roomNumber = random.nextInt( 3) + 1; // 1-3
        return sessionName+roomNumber;
    }
}
