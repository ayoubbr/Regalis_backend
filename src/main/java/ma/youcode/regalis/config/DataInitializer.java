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
    private final CategoryRepository categoryRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final PuzzleRepository puzzleRepository;
    private final UserProgressRepository userProgressRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementRepository achievementRepository;
    private final AdminNoteRepository adminNoteRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return;
        }

        // 1. Users
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@regalis.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFirstName("System");
        admin.setLastName("Admin");
        admin.setRole(Role.ADMIN);
        admin.setImageUrl("https://img.freepik.com/premium-vector/student-avatar-illustration-user-profile-icon-youth-avatar_118339-4405.jpg");
        userRepository.save(admin);

        User player1 = new User();
        player1.setUsername("KingRegalis");
        player1.setEmail("king@regalis.com");
        player1.setPassword(passwordEncoder.encode("player123"));
        player1.setFirstName("Arthur");
        player1.setLastName("Pendragon");
        player1.setRole(Role.USER);
        player1.setTotalXp(15420);
        player1.setLevel(15);
        player1.setCurrentStreak(24);
        player1.setLastActiveDate(LocalDate.now());
        player1.setImageUrl("https://api.dicebear.com/7.x/avataaars/svg?seed=King");
        userRepository.save(player1);

        User player2 = new User();
        player2.setUsername("ChessQueen");
        player2.setEmail("queen@regalis.com");
        player2.setPassword(passwordEncoder.encode("player123"));
        player2.setFirstName("Beth");
        player2.setLastName("Harmon");
        player2.setRole(Role.USER);
        player2.setTotalXp(8900);
        player2.setLevel(9);
        player2.setCurrentStreak(5);
        player2.setLastActiveDate(LocalDate.now().minusDays(1));
        player2.setImageUrl("https://api.dicebear.com/7.x/avataaars/svg?seed=Queen");
        userRepository.save(player2);

        // 2. Categories
        Category tactics = new Category();
        tactics.setName("Tactics");
        tactics.setDescription("Short-term combinations to gain an advantage");
        categoryRepository.save(tactics);

        Category endgame = new Category();
        endgame.setName("Endgame");
        endgame.setDescription("The final stage of the game with few pieces");
        categoryRepository.save(endgame);

        // 3. Modules
        Module mod1 = new Module();
        mod1.setName("The Knight's Gambit");
        mod1.setDescription("Mastering the unusual movements of the horsemen.");
        mod1.setOrderIndex(1);
        mod1.setImageUrl("https://images.unsplash.com/photo-1529699211952-734e80c4d42b?auto=format&fit=crop&w=400&q=80");
        moduleRepository.save(mod1);

        Module mod2 = new Module();
        mod2.setName("Castling Secrets");
        mod2.setDescription("The art of king safety and rook mobilization.");
        mod2.setOrderIndex(2);
        mod2.setImageUrl("https://images.unsplash.com/photo-1586165368502-1bad197a6461?auto=format&fit=crop&w=400&q=80");
        moduleRepository.save(mod2);

        // 4. Lessons
        Lesson lesson1 = new Lesson();
        lesson1.setTitle("L-Shaped Movements");
        lesson1.setContent("The knight moves in a unique L-shape: two squares in one direction and then one square perpendicular.");
        lesson1.setDifficulty(1);
        lesson1.setXpReward(50);
        lesson1.setModule(mod1);
        lessonRepository.save(lesson1);

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Forked by a Knight");
        lesson2.setContent("Learn how a single knight can attack two or more pieces simultaneously.");
        lesson2.setDifficulty(2);
        lesson2.setXpReward(100);
        lesson2.setModule(mod1);
        lessonRepository.save(lesson2);

        // 5. Puzzles
        Puzzle puzzle1 = new Puzzle();
        puzzle1.setFenPosition("r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3");
        puzzle1.setSolutionMoves("Nc3");
        puzzle1.setDifficulty(1);
        puzzle1.setXpReward(25);
        puzzle1.setMaxAttempts(3);
        puzzle1.setModule(mod1);
        puzzle1.setCategory(tactics);
        puzzleRepository.save(puzzle1);

        // 6. Achievements
        Achievement ach1 = new Achievement();
        ach1.setName("First Blood");
        ach1.setDescription("Complete your first lesson");
        ach1.setConditionValue(1);
        achievementRepository.save(ach1);

        // 7. User Progress & Achievements
        UserProgress prog1 = new UserProgress();
        prog1.setUser(player1);
        prog1.setLesson(lesson1);
        prog1.setCompleted(true);
        prog1.setCompletionDate(LocalDateTime.now().minusDays(10));
        userProgressRepository.save(prog1);

        UserAchievement uAch1 = new UserAchievement();
        uAch1.setUser(player1);
        uAch1.setAchievement(ach1);
        uAch1.setUnlockedDate(LocalDateTime.now().minusDays(10));
        userAchievementRepository.save(uAch1);

        // 8. Admin Notes
        AdminNote note1 = new AdminNote();
        note1.setUser(player1);
        note1.setMessage("Excellent progress on tactics modules.");
        note1.setCreatedAt(LocalDateTime.now());
        adminNoteRepository.save(note1);

        System.out.println(">>> Database Seeding Completed Successfully! <<<");
    }
}
