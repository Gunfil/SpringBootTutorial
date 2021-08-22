package tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tutorial.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
