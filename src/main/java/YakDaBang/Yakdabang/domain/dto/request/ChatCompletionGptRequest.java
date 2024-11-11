package YakDaBang.Yakdabang.domain.dto.request;

import YakDaBang.Yakdabang.domain.dto.type.ModelEnum;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
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

    private String role;

    private String message;

    private Integer maxTokens;

    public static ChatCompletionRequest of(ChatCompletionGptRequest request) {
        return ChatCompletionRequest.builder()
                .model(ModelEnum.GPT_3_5_TURBO.getName())
                .messages(convertChatMessage(request))
                .maxTokens(request.getMaxTokens())
                .build();
    }

    private static List<ChatMessage> convertChatMessage(ChatCompletionGptRequest request) {
        return List.of(new ChatMessage(request.getRole(), request.getMessage()));
    }
}
