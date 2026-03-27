package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.youcode.regalis.dto.leaderboardentry.LeaderboardEntryResponseDTO;
import ma.youcode.regalis.entity.LeaderboardEntry;
import ma.youcode.regalis.entity.User;
import ma.youcode.regalis.enums.Period;
import ma.youcode.regalis.enums.Role;
import ma.youcode.regalis.mapper.LeaderboardEntryMapper;
import ma.youcode.regalis.repository.LeaderboardEntryRepository;
import ma.youcode.regalis.repository.UserQuizRepository;
import ma.youcode.regalis.repository.UserRepository;
import ma.youcode.regalis.repository.UserSituationRepository;
import ma.youcode.regalis.service.LeaderboardService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LeaderboardServiceImpl implements LeaderboardService {

    private final LeaderboardEntryRepository leaderboardEntryRepository;
    private final UserRepository userRepository;
    private final UserQuizRepository userQuizRepository;
    private final UserSituationRepository userSituationRepository;
    private final LeaderboardEntryMapper leaderboardEntryMapper;

    @Override
    public List<LeaderboardEntryResponseDTO> getLeaderboard(Period period) {
        List<LeaderboardEntry> entries = leaderboardEntryRepository.findByPeriodOrderByRankAsc(period);

        // Auto-refresh if empty (first access or stale)
        if (entries.isEmpty()) {
            refreshLeaderboard(period);
            entries = leaderboardEntryRepository.findByPeriodOrderByRankAsc(period);
        }

        return entries.stream()
                .map(leaderboardEntryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void refreshLeaderboard(Period period) {
        log.info("Refreshing leaderboard for period: {}", period);

        // Clear existing entries for this period
        leaderboardEntryRepository.deleteByPeriod(period);
        leaderboardEntryRepository.flush();

        LocalDateTime now = LocalDateTime.now();
        Map<Long, Integer> userXpMap;

        switch (period) {
            case ALL_TIME:
                userXpMap = buildAllTimeRanking();
                break;
            case WEEKLY:
                LocalDateTime weekStart = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).with(LocalTime.MIN);
                userXpMap = buildPeriodRanking(weekStart, now);
                break;
            case MONTHLY:
                LocalDateTime monthStart = now.withDayOfMonth(1).with(LocalTime.MIN);
                userXpMap = buildPeriodRanking(monthStart, now);
                break;
            default:
                userXpMap = new HashMap<>();
        }

        // Create ranked entries
        List<Map.Entry<Long, Integer>> sorted = userXpMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        LocalDateTime periodStart = getPeriodStart(period, now);
        int rank = 1;
        for (Map.Entry<Long, Integer> entry : sorted) {
            User user = userRepository.findById(entry.getKey()).orElse(null);
            if (user == null) continue;

            LeaderboardEntry le = new LeaderboardEntry();
            le.setUser(user);
            le.setPeriod(period);
            le.setXp(entry.getValue());
            le.setRank(rank++);
            le.setPeriodStart(periodStart);
            le.setPeriodEnd(now);
            le.setUpdatedAt(now);
            leaderboardEntryRepository.save(le);
        }

        log.info("Leaderboard refreshed for {} — {} entries", period, sorted.size());
    }

    @Override
    @Scheduled(fixedRate = 10000 ) // Every 10 seconds / 5 minutes
    public void refreshAll() {
        log.info("Scheduled leaderboard refresh starting...");
        for (Period period : Period.values()) {
            try {
                refreshLeaderboard(period);
            } catch (Exception e) {
                log.error("Failed to refresh leaderboard for period: {}", period, e);
            }
        }
    }

    private Map<Long, Integer> buildAllTimeRanking() {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.USER)
                .filter(u -> u.getTotalXp() != null && u.getTotalXp() > 0)
                .collect(Collectors.toMap(User::getId, User::getTotalXp));
    }

    private Map<Long, Integer> buildPeriodRanking(LocalDateTime start, LocalDateTime end) {
        Map<Long, Integer> xpMap = new HashMap<>();

        // Quiz XP
        List<Object[]> quizResults = userQuizRepository.sumScoresByUserInPeriod(start, end);
        for (Object[] row : quizResults) {
            Long userId = (Long) row[0];
            int xp = ((Number) row[1]).intValue();
            if (xp > 0) xpMap.merge(userId, xp, (a, b) -> a + b);
        }

        // Puzzle XP (correct situations — one entry per distinct puzzle)
        List<Object[]> puzzleResults = userSituationRepository.sumPuzzleXpByUserInPeriod(start, end);
        for (Object[] row : puzzleResults) {
            Long userId = (Long) row[0];
            // row[1] = puzzleId (used for grouping, ignored here)
            int xp = ((Number) row[2]).intValue();
            if (xp > 0) xpMap.merge(userId, xp, (a, b) -> a + b);
        }

        // Filter to USER role only
        xpMap.entrySet().removeIf(entry ->
            userRepository.findById(entry.getKey())
                .map(u -> u.getRole() != Role.USER)
                .orElse(true)
        );

        return xpMap;
    }

    private LocalDateTime getPeriodStart(Period period, LocalDateTime now) {
        switch (period) {
            case WEEKLY:
                return now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).with(LocalTime.MIN);
            case MONTHLY:
                return now.withDayOfMonth(1).with(LocalTime.MIN);
            default:
                return LocalDateTime.of(2020, 1, 1, 0, 0);
        }
    }
}
