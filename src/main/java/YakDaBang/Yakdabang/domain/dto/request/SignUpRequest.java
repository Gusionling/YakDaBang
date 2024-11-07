package YakDaBang.Yakdabang.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName   : YakDaBang.Yakdabang.domain.dto.request
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 7.
 * Description   :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Schema(description = "providerId", example = "203912941")
    private Long providerId;

    @NotBlank
    @Schema(description = "이메일", example = "example@google.com")
    private String email;

    @NotBlank
    @Schema(description = "닉네임", example = "럭키비키")
    private String NickName;

    @NotBlank
    @Schema(description = "비밀번호", example = "1234")
    private String password;


}
