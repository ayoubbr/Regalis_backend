package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.AdminNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdminNoteRepository extends JpaRepository<AdminNote, Long> {
    List<AdminNote> findByUserId(Long userId);
}
