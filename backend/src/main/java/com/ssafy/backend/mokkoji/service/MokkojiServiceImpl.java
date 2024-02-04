package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.mokkoji.model.repository.MokkojiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static com.ssafy.backend.common.response.BaseResponseStatus.IS_EXIST_MOKKOJI_NAME;
import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_EXIST_MOKKOJI;


@RequiredArgsConstructor
@Service
@Slf4j
public class MokkojiServiceImpl implements MokkojiService {
    private final MokkojiRepository mokkojiRepository;
    @Override
    public Mokkoji addMokkoji(Mokkoji mokkoji) {
        log.info("모꼬지 이름을 체크합니다. {}",mokkoji.getMokkojiName());
        mokkojiRepository.findByMokkojiName(mokkoji.getMokkojiName())
                .ifPresent(a -> {
                    throw new BaseException(IS_EXIST_MOKKOJI_NAME);
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

    @Override
    public Mokkoji getMokkojiById(int mokkojiId) {
        return mokkojiRepository.findMokkojiByMokkojiId(mokkojiId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_MOKKOJI));
    }

}
