package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    
    @org.springframework.data.jpa.repository.Query("SELECT COALESCE(SUM(u.totalXp), 0) FROM User u")
    Long sumTotalXp();

    long countByLastActiveDateGreaterThanEqual(java.time.LocalDate date);

    java.util.List<User> findTop8ByOrderByCreatedAtDesc();

    long countByCreatedAtBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);
}
