package YakDaBang.Yakdabang.security.info;

import YakDaBang.Yakdabang.domain.dto.common.ExceptionDto;
import YakDaBang.Yakdabang.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * AbstractAuthenticationFailure class is responsible for handling authentication failure responses.
 * It provides a protected method {@code setErrorResponse} that can be used to set up a standardized
 * JSON response structure with appropriate error details and HTTP status code.
 * packageName   : YakDaBang.Yakdabang.security.info
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 5.
 */
public class AbstractAuthenticationFailure {
    /**
     * The protected modifier allows subclasses to access the setErrorResponse method,
     * enabling them to use or customize this error response behavior when handling specific types of authentication failures
     * This is useful in cases where other authentication handlers might need to tailor the error response format slightly
     * but still leverage the core functionality provided by AbstractAuthenticationFailure.
     * @param response
     * @param errorCode
     * @throws IOException
     */
    protected void setErrorResponse(
            HttpServletResponse response,
            ErrorCode errorCode
    ) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());

        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("data", null);
        result.put("error", ExceptionDto.of(errorCode));

        response.getWriter().write(JSONValue.toJSONString(result));
    }
}
