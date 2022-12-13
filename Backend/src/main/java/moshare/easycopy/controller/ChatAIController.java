package moshare.easycopy.controller;

import moshare.easycopy.entity.Answer;
import moshare.easycopy.entity.ChatParam;
import moshare.easycopy.entity.Question;
import moshare.easycopy.service.ChatAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ChatAI")
public class ChatAIController {


    @Autowired
    private ChatAIService chatAIService;

    @PostMapping("/getAnswer")
    @ResponseBody
    public String getAnswer(@RequestBody ChatParam chatParam){
        Answer answer = chatAIService.getAnswer(chatParam);
        String text = answer.getChoices().get(0).getText();
        return text;
    }
}
