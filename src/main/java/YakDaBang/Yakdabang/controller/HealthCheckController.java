package YakDaBang.Yakdabang.controller;

import YakDaBang.Yakdabang.Service.AuthService;
import YakDaBang.Yakdabang.annotation.UserId;
import YakDaBang.Yakdabang.domain.dto.common.ResponseDto;
import YakDaBang.Yakdabang.global.constants.Constants;
import YakDaBang.Yakdabang.security.info.UserPrincipal;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseDto<?> healthCheck(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseDto.ok("서버가 정상적으로 동작중입니다." + userPrincipal.getId());
    }
}
