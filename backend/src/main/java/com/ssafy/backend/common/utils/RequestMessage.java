package com.ssafy.backend.common.utils;

import com.ssafy.backend.user.model.dto.OpenviduRequestDTO;

public interface RequestMessage {
    void RequestOpenviduMessage(OpenviduRequestDTO dto);
}
