package moshare.easycopy.controller;

import moshare.easycopy.entity.Answer;
import moshare.easycopy.entity.ChatParam;
import moshare.easycopy.entity.Question;
import moshare.easycopy.service.ChatAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ChatAI")
public class ChatAIController {


    @Autowired
    private ChatAIService chatAIService;

    @PostMapping("/getAnswer")
    @ResponseBody
    public String getAnswer(@RequestBody ChatParam chatParam, @RequestParam(value = "check",required = false)String check){
        String answerText = chatAIService.getAnswer(chatParam,check);
        return answerText;
    }
}
