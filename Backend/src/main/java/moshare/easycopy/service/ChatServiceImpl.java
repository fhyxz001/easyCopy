package moshare.easycopy.service;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import moshare.easycopy.entity.Answer;
import moshare.easycopy.entity.ChatParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class ChatServiceImpl implements ChatAIService {
    @Value("${chatAI.API_KEY}")
    private String API_KEY;
    private String URL = "https://api.openai.com/v1/completions";
    private String md5Code = "e2f5e798186344470f784f353a80ef7f";
    @Override
    public String getAnswer(ChatParam question,String check) {
        JSONObject jsonObject = JSONUtil.parseObj(question);
        String result = HttpRequest.post(URL)
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .execute().body();
        log.info("result:{}", result);
        Answer answer = JSONUtil.toBean(result, Answer.class);
        String text = answer.getChoices().get(0).getText();
        //如果text不为空，将其中的\n替换成<br/>
        if(text!=null){
            text = text.replace("\n","<br/>");
        }
        return text;
    }
}
