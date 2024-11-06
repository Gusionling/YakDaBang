package YakDaBang.Yakdabang.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * GlobalLoggerFilter is a request logging filter that logs details about each HTTP request
 * and calculates the processing time for each request.
 *
 * <p>This filter extends {@link OncePerRequestFilter}, ensuring that it is executed only once per request
 * in a single request cycle. It logs the client's IP address, HTTP method, and request URI both at the start
 * and end of request processing. Additionally, it calculates and logs the time taken to process each request.
 *
 * packageName   : YakDaBang.Yakdabang.security.filter
 * Date          : 2024. 11. 6
 */
@Slf4j
public class GlobalLoggerFilter extends OncePerRequestFilter {

    /**
     * Logs incoming HTTP request details and calculates the processing time for each request.
     *
     * <p>This method logs the client IP address (from the "X-FORWARDED-FOR" header if present,
     * otherwise from the remote address), HTTP method, and request URI when a request is received.
     * It then passes the request down the filter chain. After processing, it calculates and logs
     * the total time taken to handle the request.
     *
     * @param request     the {@link HttpServletRequest} containing client request data
     * @param response    the {@link HttpServletResponse} for the response to be sent
     * @param filterChain the {@link FilterChain} allowing the request to proceed through other filters
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("[Global] HTTP Request Received! ({} {} {})",
                request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURI());

        // Store the current time to calculate request processing duration
        request.setAttribute("INTERCEPTOR_PRE_HANDLE_TIME",  System.currentTimeMillis());

        // Proceed with the next filter in the chain
        filterChain.doFilter(request, response);

        // Calculate and log the processing time
        Long preHandleTime = (Long) request.getAttribute("INTERCEPTOR_PRE_HANDLE_TIME");
        Long postHandleTime = System.currentTimeMillis();

        log.info("[Global] HTTP Request Has Been Processed! It Took {}ms. ({} {} {})",
                postHandleTime - preHandleTime,
                request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURI());
    }
}