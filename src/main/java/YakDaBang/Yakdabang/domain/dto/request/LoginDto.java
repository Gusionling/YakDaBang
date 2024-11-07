package YakDaBang.Yakdabang.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * packageName   : YakDaBang.Yakdabang.domain.dto.request
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 7.
 * Description   :
 */

@Data
@RequiredArgsConstructor
public class LoginDto {

    @NotBlank @Schema(description = "이메일", example = "example@google.com")
    private final String email;

    @NotNull @Schema(description = "비밀번호", example = "1234")
    private final String password;

}
