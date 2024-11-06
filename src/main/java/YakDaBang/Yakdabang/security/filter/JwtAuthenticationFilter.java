package YakDaBang.Yakdabang.security.filter;

import YakDaBang.Yakdabang.global.constants.Constants;
import YakDaBang.Yakdabang.security.info.UserPrincipal;
import YakDaBang.Yakdabang.security.service.CustomUserDetailService;
import YakDaBang.Yakdabang.utility.HeaderUtil;
import YakDaBang.Yakdabang.utility.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter is a filter for validating JWT tokens on incoming HTTP requests.
 *
 * <p>This filter, which extends {@link OncePerRequestFilter} to ensure it only runs once per request,
 * verifies JWT tokens present in the Authorization header of incoming requests. If a valid token is found,
 * it retrieves user details and sets up an authenticated {@link SecurityContext}.
 *
 * <p>This filter also skips certain URLs that do not require authentication, as specified by
 * {@link Constants#NO_NEED_AUTH_URLS}.
 *
 * packageName   : YakDaBang.Yakdabang.security.filter
 * Author        : imhyeong-gyu
 * Date          : 2024. 11. 6
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    /**
     * Authenticates users by validating the JWT token in the request header.
     *
     * <p>This method retrieves the JWT token from the Authorization header, validates it, and,
     * if valid, extracts the user details to create an {@link UsernamePasswordAuthenticationToken}
     * that represents the user's authentication status. This token is then set in the
     * {@link SecurityContext} to allow downstream security checks.
     *
     * @param request     the {@link HttpServletRequest} containing client request data
     * @param response    the {@link HttpServletResponse} for sending responses if needed
     * @param filterChain the {@link FilterChain} for processing the next filter in the chain
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extract and validate JWT token
        String token = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new IllegalArgumentException("Authorization Header Is Not Found!"));

        // Parse claims from the validated token
        Claims claims = jwtUtil.validateToken(token);

        // Load user details based on user ID from the token claims
        UserPrincipal userPrincipal = (UserPrincipal) customUserDetailService.loadUserById(
                claims.get(Constants.USER_ID_CLAIM_NAME, Long.class));

        // Set up the authentication token for the SecurityContext
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Set the authentication in the SecurityContext
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }

    /**
     * Determines if the filter should be skipped for the current request URI.
     *
     * <p>This method checks if the request URI matches any in a predefined list of URLs
     * that do not require authentication. If so, the filter is not applied.
     *
     * @param request the {@link HttpServletRequest} containing client request data
     * @return {@code true} if the request URI is in the no-authentication-required list; {@code false} otherwise
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Constants.NO_NEED_AUTH_URLS.contains(request.getRequestURI());
    }
}