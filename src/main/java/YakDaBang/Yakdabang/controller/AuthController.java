package YakDaBang.Yakdabang.controller;

import YakDaBang.Yakdabang.Service.AuthService;
import YakDaBang.Yakdabang.domain.dto.common.ResponseDto;
import YakDaBang.Yakdabang.domain.dto.request.SignUpRequest;
import YakDaBang.Yakdabang.domain.dto.request.UserLoginDto;
import YakDaBang.Yakdabang.global.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName   : YakDaBang.Yakdabang.controller
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 6.
 * Description   :
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.API_PREFIX + "/auth")
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인")
    @Schema(name = "login", description = "로그인")
    public ResponseDto<?> login(@RequestBody UserLoginDto userloginDto) {
        return ResponseDto.ok(authService.login(userloginDto));
    }

    @Operation(
            summary = "회원가입",
            description = "회원가입 후 토큰을 반환합니다. 카카오 로그인인 경우는 카카오 인증을 먼저 받고 실행해주세요 platform은 kakao혹은 general을 입력해주세요"
    )
    @PostMapping("/sign-up")
    public ResponseDto<?> signUp(@RequestBody SignUpRequest request) {

        return ResponseDto.ok(authService.signUp(request));

    }
}
