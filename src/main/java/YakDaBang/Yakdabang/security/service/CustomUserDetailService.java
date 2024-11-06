package YakDaBang.Yakdabang.security.service;

import YakDaBang.Yakdabang.global.exception.CommonException;
import YakDaBang.Yakdabang.global.exception.ErrorCode;
import YakDaBang.Yakdabang.repository.UserRepository;
import YakDaBang.Yakdabang.security.info.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailService loads user-specific security details for authentication and authorization.
 *
 * <p>This service interacts with the {@link UserRepository} to retrieve user information by user ID
 * and convert it into a {@link UserPrincipal} instance, which implements {@link UserDetails} required
 * by Spring Security for authentication. If the user is not found, it throws a {@link CommonException}
 * with a specific error code indicating that the user does not exist.
 * packageName   : YakDaBang.Yakdabang.security.service
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 6.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService {

    private final UserRepository userRepository;


    public UserDetails loadUserById(Long id) {
        UserRepository.UserSecurityForm user = userRepository.findSecurityFormById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LOGIN_USER));

        return UserPrincipal.createByUserSecurityForm(user);
    }
}
