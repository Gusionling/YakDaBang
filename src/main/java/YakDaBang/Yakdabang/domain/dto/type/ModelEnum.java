package YakDaBang.Yakdabang.domain.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName   : YakDaBang.Yakdabang.domain.dto.type
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 11.
 * Description   :
 */
@Getter
@AllArgsConstructor
public enum ModelEnum {
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    GPT_3_5_TURBO_0301("gpt-3.5-turbo-0301"),
    GPT_4("gpt-4"),
    GPT_4_0314("gpt-4-0314"),
    GPT_4_32K("gpt-4-32k"),
    GPT_4_32K_0314("gpt-4-32k-0314"),
    GPT_4_1106_preview("gpt-4-1106-preview");
    private String name;

}
