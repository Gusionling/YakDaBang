package YakDaBang.Yakdabang.security.handler.jwt;

import YakDaBang.Yakdabang.global.exception.ErrorCode;
import YakDaBang.Yakdabang.security.info.AbstractAuthenticationFailure;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * This class is not for Checking whether the error occured or not. Just setting error response.
 * packageName   : YakDaBang.Yakdabang.security.handler.jwt
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 5.
 * Description
 * jwtAuthEntryPoint is an authentication entry point for handling unauthorized access attempts
 * involving JWT-based authentication. When a user attempts to access a protected resource
 * without proper authentication, this class triggers the appropriate error response.
 *
 * <p>It extends {@link AbstractAuthenticationFailure} to leverage a standardized error response
 * format and implements {@link AuthenticationEntryPoint} to integrate with Spring Security's
 * authentication process.
 */
public class JwtAuthEntryPoint extends AbstractAuthenticationFailure implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        ErrorCode errorCode = request.getAttribute("exception") == null ?
                ErrorCode.NOT_FOUND_END_POINT : (ErrorCode) request.getAttribute("exception");

        setErrorResponse(response, errorCode);
    }
}
