package com.ssafy.backend.user.model.dto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import java.util.HashMap;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenviduRequestDto {
    private String session;
    private String type;
    private String data;

    public String toJson() throws JsonProcessingException {
        HashMap<String,String> json = new HashMap<>();
        json.put("session",this.session);
        json.put("type","signal:"+this.type);
        json.put("data",this.data);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(json);
    }

}
