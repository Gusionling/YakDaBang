package YakDaBang.Yakdabang.security.handler.signout;

import YakDaBang.Yakdabang.domain.dto.common.ExceptionDto;
import YakDaBang.Yakdabang.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONValue;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * CustomSignOutResultHandler handles the result of the logout process by sending a JSON response
 * to the client indicating whether the logout was successful or if there was an error.
 *
 * <p>This handler implements {@link LogoutSuccessHandler}, which is triggered upon a successful
 * logout attempt in Spring Security. If the user is not authenticated (already logged out),
 * it returns a failure response. Otherwise, it returns a success response upon logout.
 * packageName   : YakDaBang.Yakdabang.security.handler.signout
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 6.
 * Description   :
 */
@Component
@RequiredArgsConstructor
public class CustomSignOutResultHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 애초에 로그인이 안된 경우
        if (authentication == null) {
            setFailureResponse(response);
            return;
        }
        setSuccessResponse(response);
    }

    private void setSuccessResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", null);
        result.put("error", null);

        response.getWriter().write(JSONValue.toJSONString(result));
    }

    private void setFailureResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.NOT_FOUND.value());

        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("data", null);
        result.put("error", ExceptionDto.of(ErrorCode.NOT_FOUND_LOGIN_USER));

        response.getWriter().write(JSONValue.toJSONString(result));
    }
}
