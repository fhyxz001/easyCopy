package moshare.easycopy.service;

import moshare.easycopy.entity.Answer;
import moshare.easycopy.entity.ChatParam;

public interface ChatAIService {
    Answer getAnswer(ChatParam question);
}
