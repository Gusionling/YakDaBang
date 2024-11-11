package YakDaBang.Yakdabang.domain.dto.type;

/**
 * packageName   : YakDaBang.Yakdabang.domain.dto.type
 * Author        : imhyeong-gyu
 * Data          : 2024. 11. 12.
 * Description   :
 */
public enum ChatMessageRole {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant"),
    FUNCTION("function");

    private final String value;

    ChatMessageRole(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
