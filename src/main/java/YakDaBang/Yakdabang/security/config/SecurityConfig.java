package YakDaBang.Yakdabang.security.config;

import YakDaBang.Yakdabang.global.constants.Constants;
import YakDaBang.Yakdabang.security.filter.GlobalLoggerFilter;
import YakDaBang.Yakdabang.security.filter.JwtAuthenticationFilter;
import YakDaBang.Yakdabang.security.filter.JwtExceptionFilter;
import YakDaBang.Yakdabang.security.handler.jwt.JwtAuthEntryPoint;
import YakDaBang.Yakdabang.security.handler.signout.CustomSignOutProcessHandler;
import YakDaBang.Yakdabang.security.handler.signout.CustomSignOutResultHandler;
import YakDaBang.Yakdabang.security.service.CustomUserDetailService;
import YakDaBang.Yakdabang.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * SecurityConfig configures the security settings for the application, including authentication,
 * authorization, and filter chain setup.
 *
 * <p>This configuration class enables web security, sets up JWT-based stateless authentication,
 * and configures custom filters and handlers for login, logout, and error handling. It applies
 * various security configurations such as CSRF protection, session management, and access control
 * for specific URLs.
 *
 * packageName   : YakDaBang.Yakdabang.security.config
 * Author        : imhyeong-gyu
 * Date          : 2024. 11. 6
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder customPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final CustomSignOutProcessHandler customSignOutProcessHandler;
    private final CustomSignOutResultHandler customSignOutResultHandler;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final CustomUserDetailService customUserDetailService;
    private final JwtUtil jwtUtil;

    /**
     * Configures the security filter chain, defining the behavior for authentication, authorization,
     * and exception handling.
     *
     * <p>This method disables default security mechanisms like CSRF and form login, enforces stateless
     * session management, and restricts access to certain endpoints based on authentication requirements.
     * It also configures logout behavior and custom filters for JWT validation and logging.
     *
     * <p>Filter Chain Order:
     * <ul>
     *   <li>{@link GlobalLoggerFilter} - Logs incoming requests.</li>
     *   <li>{@link JwtExceptionFilter} - Catches and handles JWT-related exceptions.</li>
     *   <li>{@link JwtAuthenticationFilter} - Validates JWT tokens for authentication.</li>
     * </ul>
     *
     * @param httpSecurity the {@link HttpSecurity} object used to configure HTTP security
     * @return a {@link SecurityFilterChain} that defines the security configuration
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF protection for stateless API
                .httpBasic(AbstractHttpConfigurer::disable)  // Disable basic authentication
                .formLogin(AbstractHttpConfigurer::disable)  // Disable form-based login
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Define access rules
                .authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll()
                                .anyRequest().authenticated()
                )
                // Configure logout handling
                .logout(configurer ->
                        configurer
                                .logoutUrl("/api/v1/auth/sign-out")
                                .addLogoutHandler(customSignOutProcessHandler)
                                .logoutSuccessHandler(customSignOutResultHandler)
                )
                // Set the entry point for unauthorized requests
                .exceptionHandling(configurer ->
                        configurer
                                .authenticationEntryPoint(jwtAuthEntryPoint)
                )
                // Add custom filters in specific order
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil, customUserDetailService),
                        LogoutFilter.class)
                .addFilterBefore(
                        new JwtExceptionFilter(),
                        JwtAuthenticationFilter.class)
                .addFilterBefore(
                        new GlobalLoggerFilter(),
                        JwtExceptionFilter.class)
                .getOrBuild();
    }
}