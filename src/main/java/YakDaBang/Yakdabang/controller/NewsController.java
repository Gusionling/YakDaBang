package YakDaBang.Yakdabang.controller;

import YakDaBang.Yakdabang.Service.ChatGptService;
import YakDaBang.Yakdabang.domain.dto.common.ResponseDto;
import YakDaBang.Yakdabang.domain.dto.request.ArticleDto;
import YakDaBang.Yakdabang.domain.dto.request.ChatCompletionGptRequest;
import YakDaBang.Yakdabang.domain.dto.response.GradeResponse;
import YakDaBang.Yakdabang.global.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName   : YakDaBang.Yakdabang.controller
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 9.
 * Description   :
 */

@RestController
@RequestMapping(Constants.API_PREFIX + "/news")
@Tag(name = "News", description = "뉴스 정보를 받아오는 API")
@RequiredArgsConstructor
public class NewsController {

    private final ChatGptService chatService;

    @Operation(
            summary = "검색어로 뉴스를 받아옵니다.",
            description = "추후에는 빅카인즈 API가 될 것이다.."
    )
    @GetMapping("/search")
    public ResponseDto<?> searchNews(){

        return ResponseDto.ok("뉴스를 검색합니다.");
    }


    @Operation(
            summary = "해당 검색어의 뉴스 트랜드 받아오는 API",
            description = "추후에는 빅카인즈 API의 트랜드 검색 API가 될 것이다.."
    )
    @GetMapping("/trend")
    public ResponseDto<?> searchTrend(){

        return ResponseDto.ok("검색어의 트랜드를 받아옵니다.");
    }


    @Operation(
            summary = "검색어의 뉴스들의 긍정, 부정성을 판단하고 받은 약의 효능을 보여줄 수 있는 생활 습관 추천",
            description = "GPT야 도와줘....! 너만 믿을게"
    )
    @PostMapping("/completion/simpleChat")
    public ResponseDto<?> easyChatByGPT(
            final @RequestBody ChatCompletionGptRequest request
    ) {
        return ResponseDto.ok(chatService.completionChat(request));
    }

    @Operation(
            summary = "검색어의 뉴스들의 긍정, 부정성을 판단",
            description = "request 포멧에 맞춰서 요청을 해주세요"
    )
    @PostMapping("/completion/gradeChat")
    public ResponseDto<?> newsGradeChatByGPT(
            final @RequestBody List<ArticleDto> articles
    ) {

        // ChatGptService의 analyzeSentiment 메서드를 호출하여 List<GradeResponse> 응답을 받음
        List<GradeResponse> gradeList = chatService.analyzeSentiment(articles);

        // Map 형식으로 포맷하여 응답
        Map<String, Object> result = new HashMap<>();
        result.put("news", gradeList);

        return ResponseDto.ok(result);
    }


}
