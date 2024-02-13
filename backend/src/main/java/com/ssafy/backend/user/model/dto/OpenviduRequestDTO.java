package com.ssafy.backend.user.model.dto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponseStatus;
import lombok.*;
import java.util.HashMap;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenviduRequestDTO {
    private String session;
    private String type;
    private String data;

    public String toJson(){
        HashMap<String,String> json = new HashMap<>();
        json.put("session",this.session);
        json.put("type","signal:"+this.type);
        json.put("data",this.data);
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            throw new BaseException(BaseResponseStatus.JSON_PROCESSING_ERROR);
        }
    }

}
