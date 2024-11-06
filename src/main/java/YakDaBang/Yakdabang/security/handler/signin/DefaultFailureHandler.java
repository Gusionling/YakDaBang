package YakDaBang.Yakdabang.security.handler.signin;

import YakDaBang.Yakdabang.domain.dto.common.ExceptionDto;
import YakDaBang.Yakdabang.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONValue;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName   : YakDaBang.Yakdabang.security.handler.signin
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 6.
 * Description   :
 */
public class DefaultFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        setFailureAppResponse(response);
    }

    private void setFailureAppResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(ErrorCode.FAILURE_LOGIN.getHttpStatus().value());

        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("data", null);
        result.put("error", ExceptionDto.of(ErrorCode.FAILURE_LOGIN));

        response.getWriter().write(JSONValue.toJSONString(result));
    }
}
