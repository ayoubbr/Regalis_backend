package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.LeaderboardEntry;
import ma.youcode.regalis.enums.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LeaderboardEntryRepository extends JpaRepository<LeaderboardEntry, Long> {
    List<LeaderboardEntry> findByPeriodOrderByRankAsc(Period period);
}
