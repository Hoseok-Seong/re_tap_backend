package hoselabs.future_letter.domain.user.dao;

import hoselabs.future_letter.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndProvider(String username, String provider);

    boolean existsById(Long id);

    boolean existsByUsernameAndProvider(String username, String provider);
}
