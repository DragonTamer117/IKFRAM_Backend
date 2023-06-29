package application.models.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    @Query(value = "UPDATE users SET password = :password WHERE id = :userId", nativeQuery = true)
    void updatePassword(@Param("userId") UUID userId, @Param("password") String password);
}
