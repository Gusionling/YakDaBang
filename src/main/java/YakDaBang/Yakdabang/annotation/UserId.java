package YakDaBang.Yakdabang.annotation;

import java.lang.annotation.*;

/**
    * packageName   : YakDaBang.Yakdabang.annotation
    * Author        : imhyeong-gyu
    * Data          : 2024. 11. 14.
    * Description   :
 *
*/

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserId {
}
