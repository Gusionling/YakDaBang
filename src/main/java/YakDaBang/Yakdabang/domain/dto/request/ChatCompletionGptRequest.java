package YakDaBang.Yakdabang.domain.dto.request;

import YakDaBang.Yakdabang.domain.dto.type.ModelEnum;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName   : YakDaBang.Yakdabang.domain.dto.request
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 11.
 * Description   :
 */
@Getter
public class ChatCompletionGptRequest {

    private List<ChatMessage> messages = new ArrayList<>();  // 여러 메시지를 담는 리스트

    private Integer maxTokens;

    // 생성자: 기본 시스템 메시지와 사용자 메시지 리스트 추가
    public ChatCompletionGptRequest(String role, String message, Integer maxTokens) {
        this.maxTokens = maxTokens;
        this.messages.add(new ChatMessage(role, message));
    }

    // 메시지 추가 메서드
    public void addMessage(String role, String message) {
        this.messages.add(new ChatMessage(role, message));
    }

    public static ChatCompletionRequest of(ChatCompletionGptRequest request) {
        return ChatCompletionRequest.builder()
                .model(ModelEnum.GPT_3_5_TURBO.getName())
                .messages(request.getMessages())
                .maxTokens(request.getMaxTokens())
                .build();
    }

}
