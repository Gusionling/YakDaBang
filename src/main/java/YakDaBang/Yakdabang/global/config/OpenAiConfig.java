package YakDaBang.Yakdabang.global.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName   : YakDaBang.Yakdabang.global.config
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 11.
 * Description   :
 */

@Configuration
public class OpenAiConfig {

    @Value("${gpt.api-key}")
    private String apiToken;

    @Bean
    public OpenAiService openAiService() {
        return new OpenAiService(apiToken);
    }

}
