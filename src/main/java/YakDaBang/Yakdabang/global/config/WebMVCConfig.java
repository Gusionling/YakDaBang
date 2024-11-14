package YakDaBang.Yakdabang.global.config;

import YakDaBang.Yakdabang.intercepter.pre.UserIdArgumentResolver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * packageName   : YakDaBang.Yakdabang.global.config
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 14.
 * Description   :
 */

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMVCConfig implements WebMvcConfigurer {
    private final UserIdArgumentResolver userIdArgumentResolver;


    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(this.userIdArgumentResolver);
    }

}
