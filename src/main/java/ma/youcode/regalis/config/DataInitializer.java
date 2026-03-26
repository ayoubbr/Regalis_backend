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
        player1.setLastActiveDate(LocalDate.now());
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
        player2.setLastActiveDate(LocalDate.now());
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


        // PUZZLES
        // PUZZLES & SITUATIONS: Piece Movement (Module 1)
        
        // Rook Movement Puzzle
        Puzzle rookPuzzle = new Puzzle();
        rookPuzzle.setDifficulty(1);
        rookPuzzle.setXpReward(50);
        rookPuzzle.setModule(mod1);
        puzzleRepository.save(rookPuzzle);

        seedSituations(rookPuzzle, "4k3/8/8/8/8/8/8/R3K3 w - - 0 1", "a1a8", "Move the Rook to the 8th rank to deliver a long-range attack.");
        seedSituations(rookPuzzle, "4k3/8/8/8/8/R7/8/4K3 w - - 0 1", "a3h3", "Slide the Rook horizontally across the 3rd rank.");

        // Bishop Movement Puzzle
        Puzzle bishopPuzzle = new Puzzle();
        bishopPuzzle.setDifficulty(1);
        bishopPuzzle.setXpReward(50);
        bishopPuzzle.setModule(mod1);
        puzzleRepository.save(bishopPuzzle);

        seedSituations(bishopPuzzle, "4k3/8/8/8/8/2B5/8/4K3 w - - 0 1", "c3e5", "Move the Bishop diagonally to the center of the board.");
        seedSituations(bishopPuzzle, "4k3/8/8/B7/8/8/8/4K3 w - - 0 1", "a5e1", "Retreat the Bishop diagonally to the 1st rank.");

        // Queen Movement Puzzle
        Puzzle queenPuzzle = new Puzzle();
        queenPuzzle.setDifficulty(2);
        queenPuzzle.setXpReward(100);
        queenPuzzle.setModule(mod1);
        puzzleRepository.save(queenPuzzle);

        seedSituations(queenPuzzle, "4k3/8/8/3Q4/8/8/8/4K3 w - - 0 1", "d5h1", "Move the Queen diagonally to the corner.");
        seedSituations(queenPuzzle, "4k3/Q7/8/8/8/8/8/4K3 w - - 0 1", "a7a1", "Slide the Queen vertically down the a-file.");

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

    private void seedSituations(Puzzle puzzle, String fen, String move, String desc) {
        Situation s = new Situation();
        s.setPuzzle(puzzle);
        s.setFenPosition(fen);
        s.setCorrectMove(move);
        s.setDescription(desc);
        situationRepository.save(s);
    }
}