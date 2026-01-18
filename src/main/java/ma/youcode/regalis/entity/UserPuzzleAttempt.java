package ma.youcode.regalis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_puzzle_attempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPuzzleAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_id")
    private Puzzle puzzle;

    @Column(name = "attempts_count")
    private Integer attemptsCount;

    private Boolean solved;

    @Column(name = "time_spent_seconds")
    private Integer timeSpentSeconds;

    @Column(name = "submitted_moves")
    private String submittedMoves;

    @Column(name = "attempt_date")
    private LocalDateTime attemptDate;
}
