package YakDaBang.Yakdabang.Service;

import YakDaBang.Yakdabang.domain.dto.request.SignUpRequest;
import YakDaBang.Yakdabang.domain.dto.request.UserLoginDto;
import YakDaBang.Yakdabang.domain.dto.response.JwtTokenDto;
import YakDaBang.Yakdabang.domain.dto.type.ERole;
import YakDaBang.Yakdabang.domain.entity.User;
import YakDaBang.Yakdabang.global.exception.CommonException;
import YakDaBang.Yakdabang.global.exception.ErrorCode;
import YakDaBang.Yakdabang.repository.UserRepository;
import YakDaBang.Yakdabang.utility.JwtUtil;
import YakDaBang.Yakdabang.utility.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * packageName   : YakDaBang.Yakdabang.Service
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 6.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JwtTokenDto login(UserLoginDto userLoginDto) {
        User user;
        boolean isNewUser = false;

        Optional<User> existingUser = userRepository.findBySerialId(userLoginDto.providerId());

        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            user = userRepository.save(User.signUp(userLoginDto.providerId()));
            isNewUser = true;
        }

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(user.getId(), ERole.USER);

        if (isNewUser || !jwtTokenDto.refreshToken().equals(user.getRefreshToken())) {
            userRepository.updateRefreshTokenAndLoginStatus(user.getId(), jwtTokenDto.refreshToken(), true);
        }

        return jwtTokenDto;
    }

    @Transactional
    public JwtTokenDto signUp(SignUpRequest request) {

        // 이메일 중복 체크
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new CommonException(ErrorCode.DUPLICATE_EMAIL);
        }

        User new_user = User.signUpByRequest(request);
        userRepository.save(new_user);
        new_user.setPassword(passwordEncoder.encode(request.getPassword()));

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(new_user.getId(), ERole.USER);
        new_user.setRefreshToken(jwtTokenDto.refreshToken());

        return jwtTokenDto;
    }
}
