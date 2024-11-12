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
            description = "request 포멧에 맞춰서 요청을 해주세요 아래는 예시 입니다" +
                    "[\n" +
                    "  {\n" +
                    "    \"title\": \"수면장애를 해결하고 싶다면, 식물성 멜라토닌 함유 멜라피스\",\n" +
                    "    \"content\": \"식물성 멜라토닌 함유 멜라피스는 외부 물질과 합성 성분을 담지 않고 100% 식물성 원료로 설계되었습니다. 불면증을 겪고 계신 분들이라면 멜라피스를 통해 편안하고 깊은 휴식을 취해보시길 바랍니다.\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"title\": \"엔큐엔에이, 타트체리 함량 ↑ 마그멜라퓨어-드림 출시\",\n" +
                    "    \"content\": \"엔큐엔에이 관계자는 이 제품이 생체이용률이 높은 해수 유래 마그네슘을 사용했다고 전했습니다.\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"title\": \"잠 못 드는 현대인…3조 원 국내 수면 시장 판매 금지\",\n" +
                    "    \"content\": \"타트체리, L-테아닌, 피스타치오 추출물 등 식물성 멜라토닌 원료를 활용한 건강기능식품이 인기를 끌고 있습니다. 하지만 식약처는 약품이 위험하다고 판단하여 곧 약품 판매를 금지할 예정입니다\"\n" +
                    "  }\n" +
                    "]"

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
