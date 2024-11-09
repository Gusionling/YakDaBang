package YakDaBang.Yakdabang.controller;

import YakDaBang.Yakdabang.domain.dto.common.ResponseDto;
import YakDaBang.Yakdabang.global.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName   : YakDaBang.Yakdabang.controller
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 9.
 */

@RestController
@RequestMapping(Constants.API_PREFIX + "/user")
@Tag(name = "User", description = "사용자 유스케이스에 관련된 API")
public class UserController {

    @Operation(
            summary = "뉴스를 추가하는 API",
            description = "뉴스를 추가하는 API"
    )
    @PostMapping("/add-news")
    public ResponseDto<?> addNews(){

        return ResponseDto.ok("뉴스를 추가합니다.");
    }


    @Operation(
            summary = "스크랩한 뉴스를 가져오는 API",
            description = "스크랩한 뉴스를 가져오는 API"
    )
    @GetMapping("/archive-news")
    public ResponseDto<?> archiving(){

        return ResponseDto.ok("사용자가 스크랩한 뉴스를 가져옵니다.");
    }

}
