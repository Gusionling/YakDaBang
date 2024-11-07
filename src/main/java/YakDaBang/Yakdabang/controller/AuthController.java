package YakDaBang.Yakdabang.controller;

import YakDaBang.Yakdabang.Service.AuthService;
import YakDaBang.Yakdabang.domain.dto.common.ResponseDto;
import YakDaBang.Yakdabang.domain.dto.request.LoginDto;
import YakDaBang.Yakdabang.domain.dto.request.SignUpRequest;
import YakDaBang.Yakdabang.global.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
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

    /*@PostMapping("/login")
    @Schema(name = "login", description = "로그인")
    public ResponseDto<?> login(@RequestBody UserLoginDto userloginDto) {
        return ResponseDto.ok(authService.login(userloginDto));
    }*/

    @Operation(
            summary = "회원가입",
            description = "이메일을 통한 회원가입입니다. "
    )
    @PostMapping("/sign-up")
    public ResponseDto<?> signUp(@RequestBody SignUpRequest request) {

        return ResponseDto.ok(authService.signUp(request));
    }

    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = "로그인"
    )
    public ResponseDto<?> login(@RequestBody LoginDto loginDto) {

        return ResponseDto.ok(authService.login(loginDto));
    }
}
