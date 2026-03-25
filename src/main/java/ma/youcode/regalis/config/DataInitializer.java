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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
        if (userRepository.count() > 0)
            return;

        // USERS
        User admin = new User();
        admin.setUsername("rootMaster");
        admin.setEmail("root@regalis.com");
        admin.setPassword(passwordEncoder.encode("root123"));
        admin.setFirstName("Super");
        admin.setLastName("Admin");
        admin.setRole(Role.ADMIN);
        admin.setImageUrl("https://api.dicebear.com/7.x/avataaars/svg?seed=Admin");
        userRepository.save(admin);

        User player1 = new User();
        player1.setUsername("KnightSlayer");
        player1.setEmail("knight@regalis.com");
        player1.setPassword(passwordEncoder.encode("player123"));
        player1.setFirstName("Magnus");
        player1.setLastName("Carlsen");
        player1.setRole(Role.USER);
        player1.setTotalXp(20000);
        player1.setLevel(20);
        player1.setCurrentStreak(30);
        player1.setLastActiveDate(LocalDate.now());
        player1.setImageUrl("https://api.dicebear.com/7.x/avataaars/svg?seed=Knight");
        userRepository.save(player1);

        User player2 = new User();
        player2.setUsername("PawnCrusher");
        player2.setEmail("pawn@regalis.com");
        player2.setPassword(passwordEncoder.encode("player123"));
        player2.setFirstName("Hikaru");
        player2.setLastName("Nakamura");
        player2.setRole(Role.USER);
        player2.setTotalXp(12000);
        player2.setLevel(12);
        player2.setCurrentStreak(10);
        player2.setLastActiveDate(LocalDate.now().minusDays(2));
        player2.setImageUrl("https://api.dicebear.com/7.x/avataaars/svg?seed=Pawn");
        userRepository.save(player2);

        // MODULES
        Module mod1 = new Module();
        mod1.setName("Opening Principles");
        mod1.setDescription("Control the center and develop pieces.");
        mod1.setOrderIndex(1);
        mod1.setImageUrl("https://images.unsplash.com/photo-1603297631959-5c2c1c0e5d1f");
        moduleRepository.save(mod1);

        Module mod2 = new Module();
        mod2.setName("Middle Game Tactics");
        mod2.setDescription("Improve your tactical awareness.");
        mod2.setOrderIndex(2);
        mod2.setImageUrl("https://images.unsplash.com/photo-1580541832626-2a7131ee809f");
        moduleRepository.save(mod2);

        Module mod3 = new Module();
        mod3.setName("Endgame Mastery");
        mod3.setDescription("Convert advantages into wins.");
        mod3.setOrderIndex(3);
        mod3.setImageUrl("https://images.unsplash.com/photo-1528819622765-d6bcf132f793");
        moduleRepository.save(mod3);

        Module mod4 = new Module();
        mod4.setName("Advanced Defense");
        mod4.setDescription("Learn to defend difficult positions.");
        mod4.setOrderIndex(4);
        mod4.setImageUrl("https://images.unsplash.com/photo-1518544889280-2c5aaf1d9c8d");
        moduleRepository.save(mod4);

        // QUIZZES
        Quiz quiz1 = new Quiz();
        quiz1.setTitle("Center Control");
        quiz1.setContent("Why controlling the center is important.");
        quiz1.setDifficulty(1);
        quiz1.setXpReward(100);
        quiz1.setModule(mod1);
        quiz1.setImageUrl("https://images.unsplash.com/photo-1587888191477-e74ac6bc9c4b");
        quizRepository.save(quiz1);

        seedQuestions(quiz1,
                "Which central squares are most important to control in the opening?",
                "e4, d4, e5, d5;a1, h1, a8, h8;b2, g2, b7, g7;c3, f3, c6, f6",
                "e4, d4, e5, d5",
                "Think about the 4 squares in the very middle of the board.", 50);

        seedQuestions(quiz1,
                "What is the primary benefit of controlling the center?",
                "Greater piece mobility;Directly checking the king;Winning the game immediately;Preventing castling",
                "Greater piece mobility",
                "Center control allows your pieces to reach more areas of the board.", 50);

        Quiz quiz2 = new Quiz();
        quiz2.setTitle("Piece Development");
        quiz2.setContent("Develop pieces quickly and efficiently.");
        quiz2.setDifficulty(1);
        quiz2.setXpReward(50);
        quiz2.setModule(mod1);
        quiz2.setImageUrl("https://images.unsplash.com/photo-1619163413327-546fdb903195");
        quizRepository.save(quiz2);

        Quiz quiz3 = new Quiz();
        quiz3.setTitle("Pin Tactic");
        quiz3.setContent("Learn how to pin enemy pieces.");
        quiz3.setDifficulty(2);
        quiz3.setXpReward(90);
        quiz3.setModule(mod2);
        quizRepository.save(quiz3);

        Quiz quiz4 = new Quiz();
        quiz4.setTitle("Opposition in Endgame");
        quiz4.setContent("Master king opposition.");
        quiz4.setDifficulty(3);
        quiz4.setXpReward(120);
        quiz4.setModule(mod3);
        quizRepository.save(quiz4);

        seedQuestions(quiz2,
                "Which pieces should usually be developed first?",
                "Knights and Bishops;Rooks and Queen;Pawns only;The King",
                "Knights and Bishops",
                "Minor pieces should enter the game before the heavy hitters.", 50);

        seedQuestions(quiz3,
                "Which pieces should usually be developed first?",
                "Knights and Bishops;Rooks and Queen;Pawns only;The King",
                "Knights and Bishops",
                "Minor pieces should enter the game before the heavy hitters.", 50);

        seedQuestions(quiz4,
                "Which pieces should usually be developed first?",
                "Knights and Bishops;Rooks and Queen;Pawns only;The King",
                "Knights and Bishops",
                "Minor pieces should enter the game before the heavy hitters.", 50);

        // ... (Adding more quizzes as needed)

        // PUZZLES
        Puzzle puzzle1 = new Puzzle();
        puzzle1.setFenPosition("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1");
        puzzle1.setTitle("Opening Control");
        puzzle1.setDescription("Master the basic e4 opening response.");
        puzzle1.setSolutionMoves("d5");
        puzzle1.setDifficulty(1);
        puzzle1.setXpReward(20);
        puzzle1.setMaxAttempts(3);
        puzzle1.setModule(mod1);
        puzzleRepository.save(puzzle1);

        seedSituations(puzzle1, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1", "d5", "Black responds with the Scandinavian Defense.");
        seedSituations(puzzle1, "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 2", "Nf3", "White develops a knight toward the center.");

        Puzzle puzzle2 = new Puzzle();
        puzzle2.setFenPosition("r1bqkbnr/pppp1ppp/2n5/4p3/4P3/2N5/PPPP1PPP/R1BQKBNR w KQkq - 2 3");
        puzzle2.setTitle("Ruy Lopez Variation");
        puzzle2.setDescription("Develop the Bishop to put pressure on the Knight.");
        puzzle2.setSolutionMoves("Bb5");
        puzzle2.setDifficulty(2);
        puzzle2.setXpReward(40);
        puzzle2.setMaxAttempts(3);
        puzzle2.setModule(mod2);
        puzzleRepository.save(puzzle2);

        seedSituations(puzzle2, "r1bqkbnr/pppp1ppp/2n5/4p3/4P3/2N5/PPPP1PPP/R1BQKBNR w KQkq - 2 3", "Bb5", "The Ruy Lopez: attacking the defender of e5.");
        seedSituations(puzzle2, "r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/2N5/PPPP1PPP/R1BQKBNR b KQkq - 3 3", "a6", "Black asks the bishop to declare its intentions.");

        Puzzle puzzle3 = new Puzzle();
        puzzle3.setFenPosition("8/8/8/8/8/8/4K3/4k3 w - - 0 1");
        puzzle3.setTitle("Endgame King Walk");
        puzzle3.setDescription("Find the winning path for the King in the endgame.");
        puzzle3.setSolutionMoves("Kd2");
        puzzle3.setDifficulty(3);
        puzzle3.setXpReward(60);
        puzzle3.setMaxAttempts(2);
        puzzle3.setModule(mod3);
        puzzleRepository.save(puzzle3);

        seedSituations(puzzle3, "8/8/8/8/8/8/4K3/4k3 w - - 0 1", "Kd2", "The king moves closer to the pawn.");
        seedSituations(puzzle3, "8/8/8/8/8/8/2K5/4k3 w - - 0 1", "Kd3", "Continuing the king walk.");

        Puzzle puzzle4 = new Puzzle();
        puzzle4.setFenPosition("r3k2r/pppq1ppp/2npbn2/3Np3/2P1P3/2N1B3/PP2BPPP/R2QK2R w KQkq - 0 1");
        puzzle4.setTitle("Middle Game Pressure");
        puzzle4.setDescription("Increase the pressure on the opponent's center.");
        puzzle4.setSolutionMoves("Nxf6+");
        puzzle4.setDifficulty(4);
        puzzle4.setXpReward(100);
        puzzle4.setMaxAttempts(2);
        puzzle4.setModule(mod4);
        puzzleRepository.save(puzzle4);

        seedSituations(puzzle4, "r3k2r/pppq1ppp/2npbn2/3Np3/2P1P3/2N1B3/PP2BPPP/R2QK2R w KQkq - 0 1", "Nxf6+", "Winning a piece with a fork.");
        seedSituations(puzzle4, "r3k2r/pppq1ppp/2np1n2/3Np3/2P1P3/2N1B3/PP2BPPP/R2QK2R b KQkq - 0 1", "exf4", "Capturing the knight.");

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
        UserQuiz prog1 = new UserQuiz();
        prog1.setUser(player1);
        prog1.setQuiz(quiz1);
        prog1.setCompleted(true);
        prog1.setCompletionDate(LocalDateTime.now().minusDays(5));
        userQuizRepository.save(prog1);

        UserQuiz prog2 = new UserQuiz();
        prog2.setUser(player1);
        prog2.setQuiz(quiz3);
        prog2.setCompleted(true);
        prog2.setCompletionDate(LocalDateTime.now().minusDays(3));
        userQuizRepository.save(prog2);

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

    private void seedSituations(Puzzle puzzle, String fen, String moves, String desc) {
        Situation s = new Situation();
        s.setPuzzle(puzzle);
        s.setFenPosition(fen);
        s.setSolutionMoves(moves);
        s.setDescription(desc);
        situationRepository.save(s);
    }
}