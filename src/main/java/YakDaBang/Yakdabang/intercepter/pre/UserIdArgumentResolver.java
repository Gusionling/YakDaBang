package YakDaBang.Yakdabang.intercepter.pre;

import YakDaBang.Yakdabang.annotation.UserId;
import YakDaBang.Yakdabang.global.exception.CommonException;
import YakDaBang.Yakdabang.global.exception.ErrorCode;
import YakDaBang.Yakdabang.security.info.UserPrincipal;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * packageName   : YakDaBang.Yakdabang.intercepter.pre
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 14.
 * Description   :
 */

@Component
@Slf4j
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Long.class)
                && parameter.hasParameterAnnotation(UserId.class);
    }


    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        final Object userIdObj = webRequest.getAttribute("USER_ID", WebRequest.SCOPE_REQUEST);

        if (userIdObj == null) {
            throw new CommonException(ErrorCode.INVALID_HEADER_ERROR);
        }

        return Long.valueOf(userIdObj.toString());
    }
}
