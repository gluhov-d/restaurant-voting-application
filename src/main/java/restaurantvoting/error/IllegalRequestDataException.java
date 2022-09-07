package restaurantvoting.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

public class IllegalRequestDataException extends AppException {
    public IllegalRequestDataException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
    }
}