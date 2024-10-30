package YakDaBang.Yakdabang.domain.dto.common;

import YakDaBang.Yakdabang.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class ExceptionDto {
    @Schema(name = "code", description = "에러 코드")
    @NotNull
    private final Integer code;

    @Schema(name = "message", description = "에러 메시지")
    @NotNull private final String message;

    public ExceptionDto(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode);
    }
}
