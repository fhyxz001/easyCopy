package moshare.easycopy.entity;

import lombok.Data;

import java.util.List;

@Data
public class Answer {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
}
