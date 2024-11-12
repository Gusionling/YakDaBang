package YakDaBang.Yakdabang.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName   : YakDaBang.Yakdabang.domain.dto.request
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 12.
 * Description   :
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {

    private String title;

    private String content;

}
