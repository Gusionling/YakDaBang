package YakDaBang.Yakdabang.security.handler.signout;

import YakDaBang.Yakdabang.repository.UserRepository;
import YakDaBang.Yakdabang.security.info.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * CustomSignOutProcessHandler is responsible for handling custom sign-out logic, specifically for
 * updating the user's login status and clearing the refresh token in the database upon logout.
 *
 * <p>This handler implements the {@link LogoutHandler} interface, making it compatible with Spring Security's
 * logout mechanism. When a user logs out, this handler updates the database to set the user's login
 * status to false and removes the refresh token to ensure that the session is effectively terminated.
 * packageName   : YakDaBang.Yakdabang.security.handler.signout
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 6.
 */

@Component
@RequiredArgsConstructor
public class CustomSignOutProcessHandler implements LogoutHandler {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {
        if (authentication == null) {
            return;
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        userRepository.updateRefreshTokenAndLoginStatus(userPrincipal.getId(), null, false);
    }
}
