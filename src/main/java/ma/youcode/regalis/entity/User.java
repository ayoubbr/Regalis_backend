package ma.youcode.regalis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import ma.youcode.regalis.enums.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "total_xp")
    private Integer totalXp = 0;

    @Column(name = "level")
    private Integer level = 1;

    @Column(name = "current_streak")
    private Integer currentStreak = 0;

    @Column(name = "last_active_date")
    private LocalDate lastActiveDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserProgress> progress;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserPuzzleAttempt> puzzleAttempts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAchievement> achievements;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DailyChallenge> dailyChallenges;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LeaderboardEntry> leaderboardEntries;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AdminNote> adminNotes;

    @OneToMany(mappedBy = "challenger", cascade = CascadeType.ALL)
    private List<Challenge> challengesInitiated;

    @OneToMany(mappedBy = "opponent", cascade = CascadeType.ALL)
    private List<Challenge> challengesReceived;
}
