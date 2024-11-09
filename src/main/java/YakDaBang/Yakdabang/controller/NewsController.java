package YakDaBang.Yakdabang.controller;

import YakDaBang.Yakdabang.domain.dto.common.ResponseDto;
import YakDaBang.Yakdabang.global.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName   : YakDaBang.Yakdabang.controller
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 9.
 * Description   :
 */

@RestController
@RequestMapping(Constants.API_PREFIX + "/news")
@Tag(name = "News", description = "뉴스 정보를 받아오는 API")
public class NewsController {

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
    @GetMapping("/gpt")
    public ResponseDto<?> searchByGPT(){

        return ResponseDto.ok("뉴스를 검색합니다.");
    }


}
