package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import org.springframework.data.domain.Page;

public interface MokkojiService {

    Mokkoji addMokkoji(Mokkoji mokkoji);
    Page<Mokkoji> getMokkojiList(int page, String keyword);

    void deleteMokkoji(Mokkoji mokkoji);

    Mokkoji getMokkojiById(int mokkojiId);
}
