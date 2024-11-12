package YakDaBang.Yakdabang.Service;

import YakDaBang.Yakdabang.domain.dto.request.ArticleDto;
import YakDaBang.Yakdabang.domain.dto.request.ChatCompletionGptRequest;
import YakDaBang.Yakdabang.domain.dto.response.ChatCompletionGptResponse;
import YakDaBang.Yakdabang.domain.dto.response.GradeResponse;
import YakDaBang.Yakdabang.domain.dto.type.ChatMessageRole;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.service.OpenAiService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

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

    @Transactional
    public List<GradeResponse> analyzeSentiment(List<ArticleDto> articles) {
        // 시스템 메시지
        String systemMessage = "I’ll give you news articles, each with a title and a summary. Based on the summary, assess the sentiment of each article as either 'positive,' 'negative,' or 'ambiguous.' Label an article as 'positive' only if the sentiment is clearly and unambiguously positive, without any negative or neutral undertones. If the positivity is not absolutely clear, or if there are any mixed sentiments, label it as 'ambiguous.' Respond in JSON format with the object name 'news,' where each item in 'news' has 'news_no' and 'grade' fields. Please respond only with JSON, like this:\n\n" +
                "{\n" +
                "  \"news\": [\n" +
                "    {\"news_no\": 1, \"grade\": \"positive\"},\n" +
                "    {\"news_no\": 2, \"grade\": \"ambiguous\"},\n" +
                "    {\"news_no\": 3, \"grade\": \"negative\"}\n" +
                "  ]\n" +
                "}\n\n" +
                "Do not include any other text or formatting syntax like ```.";

        // 사용자 메시지 생성
        String userMessage = articles.stream()
                .map(article -> String.format("{\"title\": \"%s\", \"content\": \"%s\"}", article.getTitle(), article.getContent()))
                .collect(Collectors.joining(", ", "[", "]"));

        // ChatCompletionGptRequest 객체 생성
        ChatCompletionGptRequest request = new ChatCompletionGptRequest(
                ChatMessageRole.SYSTEM.value(), systemMessage, 1024);  // 시스템 메시지 설정
        request.addMessage(ChatMessageRole.USER.value(), userMessage);  // 사용자 메시지 설정

        // OpenAI API 호출 및 응답 반환
        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
                ChatCompletionGptRequest.of(request)
        );

        // ChatCompletionGptResponse 객체로 변환 후 결과를 파싱하여 반환
        ChatCompletionGptResponse response = ChatCompletionGptResponse.of(chatCompletion);
        return response.getParsedGrade();  // List<GradeResponse> 형식으로 반환

    }

}
