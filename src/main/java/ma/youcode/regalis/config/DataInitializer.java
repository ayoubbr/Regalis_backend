package ma.youcode.regalis.config;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.entity.*;
import ma.youcode.regalis.entity.Module;
import ma.youcode.regalis.enums.Role;
import ma.youcode.regalis.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModuleRepository moduleRepository;
    private final QuizRepository quizRepository;
    private final PuzzleRepository puzzleRepository;
    private final UserQuizRepository userQuizRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementRepository achievementRepository;
    private final AdminNoteRepository adminNoteRepository;
    private final QuestionRepository questionRepository;
    private final SituationRepository situationRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        // USERS
        User admin = new User();
        admin.setUsername("rootMaster");
        admin.setEmail("root@regalis.com");
        admin.setPassword(passwordEncoder.encode("root123"));
        admin.setFirstName("Super");
        admin.setLastName("Admin");
        admin.setRole(Role.ADMIN);
        admin.setImageUrl("https://cdn-icons-png.flaticon.com/128/9408/9408175.png");
        userRepository.save(admin);

        User player1 = new User();
        player1.setUsername("KnightSlayer");
        player1.setEmail("knight@regalis.com");
        player1.setPassword(passwordEncoder.encode("player123"));
        player1.setFirstName("Magnus");
        player1.setLastName("Carlsen");
        player1.setRole(Role.USER);
        player1.setTotalXp(0);
        player1.setLevel(1);
        player1.setCurrentStreak(0);
        player1.setImageUrl("https://cdn-icons-png.flaticon.com/128/1999/1999625.png");
        userRepository.save(player1);

        User player2 = new User();
        player2.setUsername("PawnCrusher");
        player2.setEmail("pawn@regalis.com");
        player2.setPassword(passwordEncoder.encode("player123"));
        player2.setFirstName("Hikaru");
        player2.setLastName("Nakamura");
        player2.setRole(Role.USER);
        player2.setTotalXp(0);
        player2.setLevel(1);
        player2.setCurrentStreak(0);
        player2.setImageUrl("https://cdn-icons-png.flaticon.com/128/201/201634.png");
        userRepository.save(player2);

        // MODULES
        Module mod1 = new Module();
        mod1.setName("Piece movement");
        mod1.setDescription("Learn how each piece move.");
        mod1.setOrderIndex(1);
        mod1.setImageUrl("https://images.unsplash.com/photo-1603297631959-5c2c1c0e5d1f");
        moduleRepository.save(mod1);

        Module mod2 = new Module();
        mod2.setName("Capturing and attacking.");
        mod2.setDescription("How to capture opponent pieces.");
        mod2.setOrderIndex(2);
        mod2.setImageUrl("https://images.pexels.com/photos/33975462/pexels-photo-33975462.jpeg");
        moduleRepository.save(mod2);

        Module mod3 = new Module();
        mod3.setName("Defending your pieces");
        mod3.setDescription("How to keep your pieces safe.");
        mod3.setOrderIndex(3);
        mod3.setImageUrl("https://images.unsplash.com/photo-1528819622765-d6bcf132f793");
        moduleRepository.save(mod3);

        Module mod4 = new Module();
        mod4.setName("Check, attacking the king");
        mod4.setDescription("Learn to attack the opponent king and win.");
        mod4.setOrderIndex(4);
        mod4.setImageUrl("https://images.unsplash.com/photo-1518544889280-2c5aaf1d9c8d");
        moduleRepository.save(mod4);

        // QUIZZES
        Quiz quiz1 = new Quiz();
        quiz1.setTitle("Center Control");
        quiz1.setContent("Why controlling the center is important.");
        quiz1.setDifficulty(1);
        quiz1.setModule(mod1);
        quiz1.setImageUrl("https://images.unsplash.com/photo-1587888191477-e74ac6bc9c4b");
        quizRepository.save(quiz1);

        seedQuestions(quiz1, "Which central squares are most important to control in the opening?", "e4, d4, e5, d5;a1, h1, a8, h8;b2, g2, b7, g7;c3, f3, c6, f6", "e4, d4, e5, d5", "Think about the 4 squares in the very middle of the board.", 50);

        seedQuestions(quiz1, "What is the primary benefit of controlling the center?", "Greater piece mobility;Directly checking the king;Winning the game immediately;Preventing castling", "Greater piece mobility", "Center control allows your pieces to reach more areas of the board.", 50);

        Quiz quiz2 = new Quiz();
        quiz2.setTitle("Piece Development");
        quiz2.setContent("Develop pieces quickly and efficiently.");
        quiz2.setDifficulty(1);
        quiz2.setModule(mod1);
        quiz2.setImageUrl("https://images.unsplash.com/photo-1619163413327-546fdb903195");
        quizRepository.save(quiz2);

        Quiz quiz3 = new Quiz();
        quiz3.setTitle("Pin Tactic");
        quiz3.setContent("Learn how to pin enemy pieces.");
        quiz3.setDifficulty(2);
        quiz3.setModule(mod2);
        quizRepository.save(quiz3);

        Quiz quiz4 = new Quiz();
        quiz4.setTitle("Opposition in Endgame");
        quiz4.setContent("Master king opposition.");
        quiz4.setDifficulty(3);
        quiz4.setModule(mod3);
        quizRepository.save(quiz4);

        seedQuestions(quiz2, "Which pieces should usually be developed first?", "Knights and Bishops;Rooks and Queen;Pawns only;The King", "Knights and Bishops", "Minor pieces should enter the game before the heavy hitters.", 50);

        seedQuestions(quiz3, "Which pieces should usually be developed first?", "Knights and Bishops;Rooks and Queen;Pawns only;The King", "Knights and Bishops", "Minor pieces should enter the game before the heavy hitters.", 50);

        seedQuestions(quiz4, "Which pieces should usually be developed first?", "Knights and Bishops;Rooks and Queen;Pawns only;The King", "Knights and Bishops", "Minor pieces should enter the game before the heavy hitters.", 50);


        // ==================== PUZZLES ====================
        // MODULE 1: Piece Movement (10 puzzles)

        Puzzle m1p1 = makePuzzle(1, 30, mod1);
        seedSituations(m1p1, "4k3/8/8/8/8/8/8/R3K3 w - - 0 1", "a1a8", "Move the Rook straight up to the 8th rank.");
        seedSituations(m1p1, "4k3/8/8/8/R7/8/8/4K3 w - - 0 1", "a4h4", "Slide the Rook across the 4th rank.");

        Puzzle m1p2 = makePuzzle(1, 30, mod1);
        seedSituations(m1p2, "4k3/8/8/8/8/8/R7/4K3 w - - 0 1", "a2a7", "Push the Rook up to the 7th rank.");
        seedSituations(m1p2, "4k3/8/8/8/8/8/4K3/7R w - - 0 1", "h1a1", "Slide the Rook all the way to the a-file.");

        Puzzle m1p3 = makePuzzle(1, 30, mod1);
        seedSituations(m1p3, "4k3/8/8/8/8/2B5/8/4K3 w - - 0 1", "c3f6", "Move the Bishop diagonally to f6.");
        seedSituations(m1p3, "4k3/8/5B2/8/8/8/8/4K3 w - - 0 1", "f6a1", "Retreat the Bishop to the a1 corner.");

        Puzzle m1p4 = makePuzzle(1, 30, mod1);
        seedSituations(m1p4, "4k3/8/8/8/8/8/8/B3K3 w - - 0 1", "a1e5", "Send the Bishop to the center of the board.");
        seedSituations(m1p4, "4k3/8/8/4B3/8/8/8/4K3 w - - 0 1", "e5h8", "Move the Bishop to the h8 corner.");

        Puzzle m1p5 = makePuzzle(1, 40, mod1);
        seedSituations(m1p5, "4k3/8/8/3Q4/8/8/8/4K3 w - - 0 1", "d5h1", "Move the Queen diagonally to the h1 corner.");
        seedSituations(m1p5, "4k3/8/8/8/8/8/8/Q3K3 w - - 0 1", "a1a8", "Push the Queen up the a-file to a8.");

        Puzzle m1p6 = makePuzzle(1, 40, mod1);
        seedSituations(m1p6, "4k3/Q7/8/8/8/8/8/4K3 w - - 0 1", "a7h7", "Slide the Queen across the 7th rank.");
        seedSituations(m1p6, "4k3/7Q/8/8/8/8/8/4K3 w - - 0 1", "h7h1", "Drop the Queen down to h1.");

        Puzzle m1p7 = makePuzzle(1, 40, mod1);
        seedSituations(m1p7, "4k3/8/8/8/8/5N2/8/4K3 w - - 0 1", "f3e5", "Jump the Knight to the strong e5 outpost.");
        seedSituations(m1p7, "4k3/8/8/4N3/8/8/8/4K3 w - - 0 1", "e5d7", "Hop the Knight from e5 to d7.");

        Puzzle m1p8 = makePuzzle(1, 40, mod1);
        seedSituations(m1p8, "4k3/8/8/8/8/8/8/N3K3 w - - 0 1", "a1b3", "Jump the Knight from a1 to b3.");
        seedSituations(m1p8, "4k3/8/8/8/8/1N6/8/4K3 w - - 0 1", "b3d4", "Move the Knight to d4 in the center.");

        Puzzle m1p9 = makePuzzle(2, 50, mod1);
        seedSituations(m1p9, "4k3/8/8/8/4K3/8/8/7R w - - 0 1", "h1h8", "Launch the Rook to h8 along the open file.");
        seedSituations(m1p9, "4k3/8/8/8/8/8/B7/4K3 w - - 0 1", "a2f7", "Shoot the Bishop diagonally to f7.");

        Puzzle m1p10 = makePuzzle(2, 50, mod1);
        seedSituations(m1p10, "4k3/8/8/8/8/8/4K3/3Q4 w - - 0 1", "d1d8", "Drive the Queen straight up to d8.");
        seedSituations(m1p10, "4k3/8/8/8/8/5N2/4K3/8 w - - 0 1", "f3g5", "Jump the Knight to the aggressive g5 square.");

        // MODULE 2: Capturing & Attacking (10 puzzles)

        Puzzle m2p1 = makePuzzle(1, 30, mod2);
        seedSituations(m2p1, "4k3/8/8/8/3p4/8/8/R3K3 w - - 0 1", "a1d1", "Move the Rook to d1 to attack the pawn.");
        seedSituations(m2p1, "4k3/8/8/8/8/3p4/8/3RK3 w - - 0 1", "d1d3", "Capture the pawn on d3 with the Rook.");

        Puzzle m2p2 = makePuzzle(1, 30, mod2);
        seedSituations(m2p2, "4k3/8/8/5p2/8/8/8/4KB2 w - - 0 1", "f1c4", "Move the Bishop towards the pawn on f5.");
        seedSituations(m2p2, "4k3/8/8/5p2/2B5/8/8/4K3 w - - 0 1", "c4f7", "Attack the f7 square with the Bishop.");

        Puzzle m2p3 = makePuzzle(1, 40, mod2);
        seedSituations(m2p3, "4k3/8/8/8/2n5/8/8/4K2R w - - 0 1", "h1c1", "Bring the Rook to the c-file to attack the knight.");
        seedSituations(m2p3, "4k3/8/8/8/2n5/8/8/2R1K3 w - - 0 1", "c1c4", "Capture the knight on c4 with the Rook.");

        Puzzle m2p4 = makePuzzle(1, 40, mod2);
        seedSituations(m2p4, "4k3/8/8/8/8/b7/8/4K2N w - - 0 1", "h1g3", "Jump the Knight to g3, away from attack.");
        seedSituations(m2p4, "4k3/8/8/8/8/b5N1/8/4K3 w - - 0 1", "g3f5", "Leap the Knight to the strong f5 square.");

        Puzzle m2p5 = makePuzzle(2, 50, mod2);
        seedSituations(m2p5, "4k3/8/8/3p4/8/8/8/3QK3 w - - 0 1", "d1d5", "Capture the pawn on d5 with the Queen.");
        seedSituations(m2p5, "4k3/8/8/3Q4/8/8/8/4K3 w - - 0 1", "d5a8", "Pin the diagonal to a8 with the Queen.");

        Puzzle m2p6 = makePuzzle(2, 50, mod2);
        seedSituations(m2p6, "4k3/8/5n2/8/8/8/8/4K2R w - - 0 1", "h1h6", "Lift the Rook to h6 controlling the 6th rank.");
        seedSituations(m2p6, "4k3/8/5n1R/8/8/8/8/4K3 w - - 0 1", "h6f6", "Capture the knight on f6 with the Rook.");

        Puzzle m2p7 = makePuzzle(2, 50, mod2);
        seedSituations(m2p7, "4k3/8/8/8/4b3/8/8/2B1K3 w - - 0 1", "c1g5", "Counter-attack by moving your Bishop to g5.");
        seedSituations(m2p7, "4k3/8/8/6B1/4b3/8/8/4K3 w - - 0 1", "g5e3", "Trade bishops by capturing on e3.");

        Puzzle m2p8 = makePuzzle(2, 60, mod2);
        seedSituations(m2p8, "4k3/3r4/8/8/8/8/8/R3K3 w - - 0 1", "a1d1", "Challenge the d-file by moving the Rook to d1.");
        seedSituations(m2p8, "4k3/3r4/8/8/8/8/8/3RK3 w - - 0 1", "d1d7", "Capture the enemy Rook on d7.");

        Puzzle m2p9 = makePuzzle(2, 60, mod2);
        seedSituations(m2p9, "4k3/8/8/8/5n2/3N4/8/4K3 w - - 0 1", "d3f4", "Capture the enemy knight on f4.");
        seedSituations(m2p9, "4k3/8/8/8/5N2/8/8/4K3 w - - 0 1", "f4e6", "Jump to the dominant e6 square.");

        Puzzle m2p10 = makePuzzle(2, 60, mod2);
        seedSituations(m2p10, "4k3/4p3/8/8/8/8/8/4K2B w - - 0 1", "h1d5", "Aim the Bishop at the center targeting e6.");
        seedSituations(m2p10, "4k3/4p3/8/3B4/8/8/8/4K3 w - - 0 1", "d5e6", "Advance the Bishop to e6 pressuring the pawn.");

        // MODULE 3: Defending Your Pieces (10 puzzles)

        Puzzle m3p1 = makePuzzle(1, 30, mod3);
        seedSituations(m3p1, "4k3/8/8/8/8/r7/8/R3K3 w - - 0 1", "a1a3", "Block the enemy rook's attack by moving to a3.");
        seedSituations(m3p1, "4k3/8/8/8/8/R7/8/4K3 w - - 0 1", "a3e3", "Slide the Rook to e3 to guard the King.");

        Puzzle m3p2 = makePuzzle(1, 30, mod3);
        seedSituations(m3p2, "4k3/8/8/3b4/8/8/8/2B1K3 w - - 0 1", "c1e3", "Block the enemy Bishop's diagonal with your Bishop.");
        seedSituations(m3p2, "4k3/8/8/3b4/8/4B3/8/4K3 w - - 0 1", "e3d2", "Retreat the Bishop to d2 for safety.");

        Puzzle m3p3 = makePuzzle(1, 40, mod3);
        seedSituations(m3p3, "4k3/8/8/4n3/8/8/8/4KN2 w - - 0 1", "f1d2", "Move your Knight to d2 to guard the King.");
        seedSituations(m3p3, "4k3/8/8/4n3/8/8/3N4/4K3 w - - 0 1", "d2f3", "Jump your Knight to f3 to block the attack.");

        Puzzle m3p4 = makePuzzle(1, 40, mod3);
        seedSituations(m3p4, "4k3/8/8/8/r7/8/8/R3K3 w - - 0 1", "e1d1", "Step the King away from the open file.");
        seedSituations(m3p4, "4k3/8/8/8/r7/8/8/R2K4 w - - 0 1", "a1a4", "Capture the attacking rook on a4.");

        Puzzle m3p5 = makePuzzle(2, 50, mod3);
        seedSituations(m3p5, "4k3/8/8/8/8/5n2/4K3/7R w - - 0 1", "h1h3", "Guard the 3rd rank from the enemy Knight.");
        seedSituations(m3p5, "4k3/8/8/8/8/5n1R/4K3/8 w - - 0 1", "h3f3", "Capture the enemy Knight on f3.");

        Puzzle m3p6 = makePuzzle(2, 50, mod3);
        seedSituations(m3p6, "4k3/8/8/8/1b6/8/8/4KB2 w - - 0 1", "f1d3", "Interpose your Bishop on d3 to block the attack.");
        seedSituations(m3p6, "4k3/8/8/8/1b6/3B4/8/4K3 w - - 0 1", "d3b5", "Counter-attack by moving to b5.");

        Puzzle m3p7 = makePuzzle(2, 50, mod3);
        seedSituations(m3p7, "4k3/8/8/8/q7/8/8/R3K3 w - - 0 1", "a1a4", "Exchange the Rook for the enemy Queen on a4.");
        seedSituations(m3p7, "4k3/8/8/8/R7/8/8/4K3 w - - 0 1", "a4a8", "Push the Rook to a8 with check.");

        Puzzle m3p8 = makePuzzle(2, 60, mod3);
        seedSituations(m3p8, "4k3/4r3/8/8/8/8/8/R3K3 w - - 0 1", "a1a8", "Counter-pin by moving the Rook to a8.");
        seedSituations(m3p8, "4k3/4r3/8/8/8/8/8/4K2R w - - 0 1", "h1e1", "Defend the King with Rook to e1.");

        Puzzle m3p9 = makePuzzle(2, 60, mod3);
        seedSituations(m3p9, "4k3/8/3n4/8/8/8/4K3/7B w - - 0 1", "h1f3", "Move the Bishop to f3 covering e4 and d5.");
        seedSituations(m3p9, "4k3/8/3n4/8/8/5B2/4K3/8 w - - 0 1", "f3d5", "Advance to d5 challenging the Knight.");

        Puzzle m3p10 = makePuzzle(2, 60, mod3);
        seedSituations(m3p10, "4k3/8/8/2q5/8/8/4K3/3R4 w - - 0 1", "d1d5", "Sacrifice the Rook to remove the Queen.");
        seedSituations(m3p10, "4k3/8/8/8/8/3N4/4K3/8 w - - 0 1", "d3e5", "Centralize the Knight on the strong e5 square.");




        // ACHIEVEMENTS
        Achievement ach1 = new Achievement();
        ach1.setName("Getting Started");
        ach1.setDescription("Complete your first quiz");
        ach1.setConditionValue(1);
        achievementRepository.save(ach1);

        Achievement ach2 = new Achievement();
        ach2.setName("Tactician");
        ach2.setDescription("Solve 10 puzzles");
        ach2.setConditionValue(10);
        achievementRepository.save(ach2);

        // USER QUIZ
//        UserQuiz prog1 = new UserQuiz();
//        prog1.setUser(player1);
//        prog1.setQuiz(quiz1);
//        prog1.setCompleted(true);
//        prog1.setCompletionDate(LocalDateTime.now().minusDays(5));
//        userQuizRepository.save(prog1);
//
//        UserQuiz prog2 = new UserQuiz();
//        prog2.setUser(player1);
//        prog2.setQuiz(quiz3);
//        prog2.setCompleted(true);
//        prog2.setCompletionDate(LocalDateTime.now().minusDays(3));
//        userQuizRepository.save(prog2);

        // USER ACHIEVEMENTS
        UserAchievement uAch1 = new UserAchievement();
        uAch1.setUser(player1);
        uAch1.setAchievement(ach1);
        uAch1.setUnlockedDate(LocalDateTime.now().minusDays(5));
        userAchievementRepository.save(uAch1);

        UserAchievement uAch2 = new UserAchievement();
        uAch2.setUser(player1);
        uAch2.setAchievement(ach2);
        uAch2.setUnlockedDate(LocalDateTime.now().minusDays(1));
        userAchievementRepository.save(uAch2);

        // ADMIN NOTES
        AdminNote note1 = new AdminNote();
        note1.setUser(player1);
        note1.setMessage("Strong tactical improvement. Keep going.");
        note1.setCreatedAt(LocalDateTime.now());
        adminNoteRepository.save(note1);

        AdminNote note2 = new AdminNote();
        note2.setUser(player2);
        note2.setMessage("Needs improvement in endgames.");
        note2.setCreatedAt(LocalDateTime.now());
        adminNoteRepository.save(note2);

        System.out.println(">>> Clean Data Seeding Completed <<<");
    }

    private void seedQuestions(Quiz quiz, String text, String options, String correct, String hint, int xp) {
        Question q = new Question();
        q.setQuiz(quiz);
        q.setText(text);
        q.setOptions(options);
        q.setCorrectOptionId(correct);
        q.setHint(hint);
        q.setXpReward(xp);
        questionRepository.save(q);
    }

    private void seedSituations(Puzzle puzzle, String fen, String move, String desc) {
        Situation s = new Situation();
        s.setPuzzle(puzzle);
        s.setFenPosition(fen);
        s.setCorrectMove(move);
        s.setDescription(desc);
        situationRepository.save(s);
    }

    private Puzzle makePuzzle(int difficulty, int xpReward, Module module) {
        Puzzle p = new Puzzle();
        p.setDifficulty(difficulty);
        p.setXpReward(xpReward);
        p.setModule(module);
        puzzleRepository.save(p);
        return p;
    }
}