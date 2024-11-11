package YakDaBang.Yakdabang.Service;

import YakDaBang.Yakdabang.domain.dto.request.ChatCompletionGptRequest;
import YakDaBang.Yakdabang.domain.dto.response.ChatCompletionGptResponse;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName   : YakDaBang.Yakdabang.Service
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 11.
 * Description   :
 */

@Service
@RequiredArgsConstructor
public class ChatGptService {

    private final OpenAiService openAiService;

    @Transactional
    public ChatCompletionGptResponse completionChat(ChatCompletionGptRequest gptCompletionChatRequest) {

        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
                ChatCompletionGptRequest.of(gptCompletionChatRequest));

       return ChatCompletionGptResponse.of(chatCompletion);

        /*List<String> messages = response.getMessages().stream()
                .map(ChatCompletionGptResponse.Message::getMessage)
                .collect(Collectors.toList());
        Answer answer = saveAnswer(messages);
        saveQuestion(gptCompletionChatRequest.getMessage(), answer);*/
    }

}
