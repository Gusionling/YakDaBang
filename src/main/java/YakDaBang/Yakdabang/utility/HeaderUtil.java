package YakDaBang.Yakdabang.utility;

import YakDaBang.Yakdabang.global.exception.CommonException;
import YakDaBang.Yakdabang.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * HeaderUtil provides utility methods for handling and validating HTTP headers.
 *
 * <p>This utility class includes methods for processing headers and refining their values.
 * It ensures that a specified header is correctly formatted and includes a given prefix.
 * If the header is missing or incorrectly formatted, an exception is thrown to indicate the issue.
 *
 * packageName   : YakDaBang.Yakdabang.utility
 * Author        : imhyeong-gyu
 * Date          : 2024. 11. 6
 */
public class HeaderUtil {

    /**
     * Extracts and validates a header's value, ensuring it contains the specified prefix.
     *
     * <p>This method retrieves the value of the specified header from the HTTP request,
     * verifies that it is non-empty and starts with the required prefix, and then
     * returns the value without the prefix. If the header is missing, empty, or does
     * not start with the prefix, it throws a {@link CommonException} with an
     * {@link ErrorCode#INVALID_HEADER_ERROR}.
     *
     * @param request the {@link HttpServletRequest} from which the header is extracted
     * @param header  the name of the header to retrieve and validate
     * @param prefix  the prefix that the header's value should start with
     * @return an {@link Optional} containing the header value without the prefix
     * @throws CommonException if the header is missing, empty, or does not start with the specified prefix
     */
    public static Optional<String> refineHeader(HttpServletRequest request, String header, String prefix) {
        String unpreparedToken = request.getHeader(header);

        if (!StringUtils.hasText(unpreparedToken) || !unpreparedToken.startsWith(prefix)) {
            throw new CommonException(ErrorCode.INVALID_HEADER_ERROR);
        }

        return Optional.of(unpreparedToken.substring(prefix.length()));
    }
}