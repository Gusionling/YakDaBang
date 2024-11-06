package YakDaBang.Yakdabang.security.info;

import YakDaBang.Yakdabang.domain.dto.type.ERole;
import YakDaBang.Yakdabang.global.constants.Constants;
import YakDaBang.Yakdabang.repository.UserRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * UserPrincipal represents the authenticated user's details and authorities within the application.
 *
 * <p>This class implements {@link UserDetails}, which is a core interface in Spring Security,
 * and provides essential information about the authenticated user, such as ID, password, role,
 * and authorities. It serves as a bridge between the application's user data and Spring Security's
 * authentication mechanisms.
 *
 * <p>UserPrincipal can be created either from a {@link UserRepository.UserSecurityForm} or directly
 * by user ID and password. It also provides methods required by {@link UserDetails} for account status,
 * packageName   : YakDaBang.Yakdabang.security.info
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 6.
 * Description   :
 */
@Builder
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    @Getter
    private final Long id;
    @Getter private final ERole role;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Creates a UserPrincipal instance using a {@link UserRepository.UserSecurityForm}.
     *
     * <p>This method constructs a UserPrincipal based on data from a {@link UserRepository.UserSecurityForm},
     * setting up the user's ID, password, and a default authority using the {@link Constants#USER_ROLE}.
     *
     * @param form the {@link UserRepository.UserSecurityForm} containing user security details
     * @return a new UserPrincipal instance with information from the form
     */
    public static UserPrincipal createByUserSecurityForm(UserRepository.UserSecurityForm form) {
        return UserPrincipal.builder()
                .id(form.getId())
                .password(form.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority(Constants.USER_ROLE)))
                .build();
    }

    /**
     * Creates a UserPrincipal instance with a specified user ID and password.
     *
     * <p>This method constructs a UserPrincipal by directly using the user's ID and password,
     * assigning the {@link Constants#USER_ROLE} authority by default.
     *
     * @param userId   the unique identifier of the user
     * @param password the user's password
     * @return a new UserPrincipal instance with the given user ID and password
     */
    public static UserPrincipal createByUserId(Long userId, String password) {
        return UserPrincipal.builder()
                .id(userId)
                .authorities(Collections.singleton(new SimpleGrantedAuthority(Constants.USER_ROLE)))
                .password(password)
                .build();
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
