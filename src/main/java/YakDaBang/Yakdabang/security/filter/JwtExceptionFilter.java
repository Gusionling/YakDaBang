package YakDaBang.Yakdabang.security.filter;

import YakDaBang.Yakdabang.global.constants.Constants;
import YakDaBang.Yakdabang.global.exception.CommonException;
import YakDaBang.Yakdabang.global.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtExceptionFilter handles exceptions related to JWT validation and processing during request filtering.
 *
 * <p>This filter, which extends {@link OncePerRequestFilter} to ensure it runs only once per request,
 * catches various JWT-related exceptions that may arise during the filter chain execution. Based on the
 * exception type, it logs the error and sets a specific error code attribute on the request for further handling.
 *
 * <p>This filter skips certain URLs that do not require authentication, as specified by {@link Constants#NO_NEED_AUTH_URLS}.
 *
 * packageName   : YakDaBang.Yakdabang.security.filter
 * Author        : imhyeong-gyu
 * Date          : 2024. 11. 6
 */
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    /**
     * Filters incoming requests to catch and handle JWT-related exceptions.
     *
     * <p>This method wraps the filter chain execution in a try-catch block to handle exceptions
     * related to JWT processing. It catches specific JWT exceptions such as {@link MalformedJwtException},
     * {@link ExpiredJwtException}, and {@link UnsupportedJwtException}, among others, logs the error, and
     * sets an appropriate error code on the request attributes. This allows downstream components to handle
     * the error accordingly.
     *
     * @param request     the {@link HttpServletRequest} containing client request data
     * @param response    the {@link HttpServletResponse} used to send responses if needed
     * @param filterChain the {@link FilterChain} for processing the next filter in the chain
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SecurityException e) {
            log.error("FilterException: SecurityException - {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.ACCESS_DENIED);
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException e) {
            log.error("FilterException: MalformedJwtException - {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.TOKEN_MALFORMED_ERROR);
            filterChain.doFilter(request, response);
        } catch (IllegalArgumentException e) {
            log.error("FilterException: IllegalArgumentException - {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.TOKEN_TYPE_ERROR);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.error("FilterException: ExpiredJwtException - {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN_ERROR);
            filterChain.doFilter(request, response);
        } catch (UnsupportedJwtException e) {
            log.error("FilterException: UnsupportedJwtException - {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.TOKEN_UNSUPPORTED_ERROR);
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            log.error("FilterException: JwtException - {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.TOKEN_UNKNOWN_ERROR);
            filterChain.doFilter(request, response);
        } catch (CommonException e) {
            log.error("FilterException: CommonException - {}", e.getMessage());
            request.setAttribute("exception", e.getErrorCode());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("FilterException: General Exception - {}", e.getMessage());
            request.setAttribute("exception", ErrorCode.INTERNAL_SERVER_ERROR);
            filterChain.doFilter(request, response);
        }
    }

    /**
     * Determines if this filter should be skipped for the current request URI.
     *
     * <p>This method checks if the request URI matches any URL in the predefined list of URLs
     * that do not require JWT authentication. If the URI is in the list, the filter is not applied.
     *
     * @param request the {@link HttpServletRequest} containing client request data
     * @return {@code true} if the request URI is in the no-authentication-required list; {@code false} otherwise
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Constants.NO_NEED_AUTH_URLS.contains(request.getRequestURI());
    }
}