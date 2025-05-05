package hoselabs.re_tap.domain.user.dao;

import hoselabs.re_tap.domain.user.entity.User;
import hoselabs.re_tap.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFindDao {

    private final UserRepository userRepository;

    public User findById(final Long userId) {
        final Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(() -> new UserNotFoundException(userId));
        return user.get();
    }

    public User findByUsername(final String username) {
        final Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UserNotFoundException(username));
        return user.get();
    }

    public Optional<User> findByUsernameAndProvider(final String username, final String provider) {
        return userRepository.findByUsernameAndProvider(username, provider);
    }
}
