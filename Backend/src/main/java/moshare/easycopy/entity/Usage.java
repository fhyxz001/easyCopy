package moshare.easycopy.entity;

import lombok.Data;

@Data
public class Usage {
    private Integer prompt_tokens;
    private Integer completion_tokens;
    private Integer total_tokens;
}
