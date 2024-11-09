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
@RequestMapping(Constants.API_PREFIX + "/medicine")
@Tag(name = "Medicine", description = "약정보 관련 API")
public class MedicineController {

    @Operation(
            summary = "정확한 약품명으로 약품의 정보를 받아오는 API",
            description = "로컬 DB에 저장되어 있는 약품의 정보(이름, 효능, 주의사항, 성분을) 받아옵니다."
    )
    @GetMapping("/search")
    public ResponseDto<?> searchByName(){

        return ResponseDto.ok("이름으로 약품의 정보를 받아옵니다.");
    }

    @Operation(
            summary = "성분으로 약품의 정보를 받아오는 API",
            description = "해당 성분이 포함된, 로컬 DB에 저장되어 있는 약품의 정보(이름, 효능, 주의사항, 성분을) 받아옵니다."
    )
    @GetMapping("/searchI")
    public ResponseDto<?> searchByIngredient(){

        return ResponseDto.ok("성분으로 약품의 정보를 받아옵니다.");
    }




}
