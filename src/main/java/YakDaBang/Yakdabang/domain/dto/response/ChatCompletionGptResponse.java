package YakDaBang.Yakdabang.domain.dto.response;

import YakDaBang.Yakdabang.domain.dto.request.ChatCompletionGptRequest;
import YakDaBang.Yakdabang.global.exception.CommonException;
import YakDaBang.Yakdabang.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.messages.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
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
@Slf4j
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


    // 응답 메시지를 GradeResponse 리스트로 변환
    public List<GradeResponse> getParsedGrade() {
        List<GradeResponse> gradeList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        // 메시지의 JSON 응답을 찾고 파싱
        for (Message message : messages) {
            log.info("message: {}", message.getMessage());
            if ("assistant".equals(message.getRole())) {
                String content = message.getMessage();

                try {
                    // JSON 형식의 메시지 내용 추출
                    JsonNode root = objectMapper.readTree(content);

                    // 'news' 배열이 존재하고 배열인지 확인
                    JsonNode gradeArray = root.path("news");
                    if (!gradeArray.isArray()) {
                        throw new CommonException(ErrorCode.PARSE_ERROR);  // 예상한 배열이 아닐 경우 예외 발생
                    }

                    for (JsonNode gradeNode : gradeArray) {
                        // 각 항목에 필요한 필드가 있는지와 타입을 확인
                        if (!gradeNode.has("news_no") || !gradeNode.get("news_no").isInt()) {
                            throw new CommonException(ErrorCode.PARSE_ERROR);  // 'news_no'가 없거나 정수가 아니면 예외 발생
                        }
                        if (!gradeNode.has("grade") || !gradeNode.get("grade").isTextual()) {
                            throw new CommonException(ErrorCode.PARSE_ERROR);  // 'grade'가 없거나 문자열이 아니면 예외 발생
                        }

                        // 필요한 필드가 모두 검증된 후 데이터를 파싱
                        int newsNo = gradeNode.get("news_no").asInt();
                        String grade = gradeNode.get("grade").asText();
                        gradeList.add(new GradeResponse(newsNo, grade));
                    }
                } catch (IOException e) {
                    throw new CommonException(ErrorCode.PARSE_ERROR);  // JSON 파싱 오류 시 예외 발생
                }
            }
        }
        return gradeList;
    }

}
