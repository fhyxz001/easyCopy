package moshare.easycopy.entity;

import lombok.Data;

@Data
public class ChatParam {
    String model;
    String prompt;
    Integer temperature;
    Integer max_tokens;
}
