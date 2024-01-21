package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.mokkoji.model.repository.MokkojiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;


@RequiredArgsConstructor
@Service
@Slf4j
public class MokkojiServiceImpl implements MokkojiService {
    private final MokkojiRepository mokkojiRepository;
    @Override
    public Mokkoji createMokkoji(Mokkoji mokkoji) {
        log.info("모꼬지 이름을 체크합니다. {}",mokkoji.getMokkojiName());
        mokkojiRepository.findByMokkojiName(mokkoji.getMokkojiName())
                .ifPresent(a -> {
                    throw new MyException("이미 모꼬지 이름이 존재합니다", HttpStatus.BAD_REQUEST);
                });
        return mokkojiRepository.save(mokkoji);

    }
    @Override
    public Page<Mokkoji> getMokkojiList(int page, String keyword) {
        ArrayList<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
        return mokkojiRepository.findByMokkojiNameContaining(keyword,pageable);
    }

    @Override
    @Transactional
    public void deleteMokkoji(Mokkoji mokkoji) {
        mokkojiRepository.delete(mokkoji);

    }
}
