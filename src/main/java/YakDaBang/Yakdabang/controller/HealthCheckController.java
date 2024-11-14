package YakDaBang.Yakdabang.controller;

import YakDaBang.Yakdabang.domain.dto.common.ResponseDto;
import YakDaBang.Yakdabang.global.constants.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName   : YakDaBang.Yakdabang.controller
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 13.
 * Description   :
 */
@RestController
@RequestMapping(Constants.API_PREFIX + "/health")
public class HealthCheckController {

    @GetMapping
    public ResponseDto<?> healthCheck(){
        return ResponseDto.ok("서버가 정상적으로 동작중입니다.");
    }

}
