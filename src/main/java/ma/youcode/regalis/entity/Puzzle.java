package ma.youcode.regalis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "puzzles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Puzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fen_position", nullable = false)
    private String fenPosition;

    @Column(name = "solution_moves", nullable = false)
    private String solutionMoves;

    private Integer difficulty;

    @Column(name = "xp_reward")
    private Integer xpReward;

    @Column(name = "max_attempts")
    private Integer maxAttempts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

    @OneToMany(mappedBy = "puzzle", cascade = CascadeType.ALL)
    private List<UserPuzzleAttempt> puzzleAttempts;

    @OneToMany(mappedBy = "puzzle", cascade = CascadeType.ALL)
    private List<DailyChallenge> dailyChallenges;
}
