package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.leaderboardentry.LeaderboardEntryResponseDTO;
import ma.youcode.regalis.entity.LeaderboardEntry;
import ma.youcode.regalis.entity.User;
import ma.youcode.regalis.enums.Period;
import ma.youcode.regalis.mapper.LeaderboardEntryMapper;
import ma.youcode.regalis.repository.LeaderboardEntryRepository;
import ma.youcode.regalis.repository.UserRepository;
import ma.youcode.regalis.service.LeaderboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaderboardServiceImpl implements LeaderboardService {

    private final LeaderboardEntryRepository leaderboardEntryRepository;
    private final UserRepository userRepository;
    private final LeaderboardEntryMapper leaderboardEntryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<LeaderboardEntryResponseDTO> getLeaderboard(Period period) {
        return leaderboardEntryRepository.findByPeriodOrderByRankAsc(period).stream()
                .map(leaderboardEntryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateLeaderboard(Period period) {
        // Clear existing entries for this period
        List<LeaderboardEntry> existingEntries = leaderboardEntryRepository.findByPeriodOrderByRankAsc(period);
        leaderboardEntryRepository.deleteAll(existingEntries);

        // Get all users sorted by XP
        List<User> users = userRepository.findAll().stream()
                .filter(u -> u.getTotalXp() != null && u.getTotalXp() > 0)
                .sorted(Comparator.comparing(User::getTotalXp).reversed())
                .toList();

        // Create new leaderboard entries
        AtomicInteger rank = new AtomicInteger(1);
        for (User user : users) {
            LeaderboardEntry entry = new LeaderboardEntry();
            entry.setUser(user);
            entry.setPeriod(period);
            entry.setXp(user.getTotalXp());
            entry.setRank(rank.getAndIncrement());
            leaderboardEntryRepository.save(entry);
        }
    }
}
