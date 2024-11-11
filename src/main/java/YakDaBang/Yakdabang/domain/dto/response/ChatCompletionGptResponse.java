package YakDaBang.Yakdabang.domain.dto.response;

import YakDaBang.Yakdabang.domain.dto.request.ChatCompletionGptRequest;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.messages.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName   : YakDaBang.Yakdabang.domain.dto.response
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 12.
 * Description   :
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionGptResponse {

    private String id;

    private String object;

    private Long created;

    private String model;

    private List<Message> messages;



    private Usage usage;
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String message;
        public static Message of(ChatMessage chatMessage) {
            return new Message(
                    chatMessage.getRole(),
                    chatMessage.getContent()
            );
        }
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage {
        private Long promptTokens;
        private Long completionTokens;
        private Long totalTokens;
        public static Usage of(com.theokanning.openai.Usage usage) {
            return new Usage(
                    usage.getPromptTokens(),
                    usage.getCompletionTokens(),
                    usage.getTotalTokens()
            );
        }
    }
    public static List<ChatCompletionGptResponse.Message> toResponseListBy(List<ChatCompletionChoice> choices) {
        return choices.stream()
                .map(completionChoice -> Message.of(completionChoice.getMessage()))
                .collect(Collectors.toList());
    }
    public static ChatCompletionGptResponse of(ChatCompletionResult result) {
        return new ChatCompletionGptResponse(
                result.getId(),
                result.getObject(),
                result.getCreated(),
                result.getModel(),
                toResponseListBy(result.getChoices()),
                Usage.of(result.getUsage())
        );
    }

}
