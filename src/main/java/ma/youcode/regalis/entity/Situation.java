package ma.youcode.regalis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "situations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Situation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fen_position", nullable = false)
    private String fenPosition;

    @Column(name = "solution_moves", nullable = false)
    private String solutionMoves;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_id")
    private Puzzle puzzle;
}
