package hoselabs.future_letter.domain.user.exception;

import hoselabs.future_letter.global.error.exception.DataNotFoundException;

public class UserNotFoundException extends DataNotFoundException {
    public UserNotFoundException(Long userId) {
        super(userId + " is not found");
    }

    public UserNotFoundException(String username) {
        super(username + " is not found");
    }

    public UserNotFoundException(String username, String provider) {
        super("User Not Found with username: " + username + " / provider: " + provider);
    }
}
