package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.admin.DashboardActivityDTO;
import ma.youcode.regalis.dto.admin.DashboardStatsDTO;
import ma.youcode.regalis.dto.adminnote.AdminNoteCreateDTO;
import ma.youcode.regalis.dto.adminnote.AdminNoteResponseDTO;
import ma.youcode.regalis.entity.AdminNote;
import ma.youcode.regalis.entity.User;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.AdminNoteMapper;
import ma.youcode.regalis.repository.AdminNoteRepository;
import ma.youcode.regalis.repository.ModuleRepository;
import ma.youcode.regalis.repository.PuzzleRepository;
import ma.youcode.regalis.repository.UserRepository;
import ma.youcode.regalis.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminNoteRepository adminNoteRepository;
    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;
    private final PuzzleRepository puzzleRepository;
    private final AdminNoteMapper adminNoteMapper;

    @Override
    public AdminNoteResponseDTO createAdminNote(AdminNoteCreateDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.userId()));

        AdminNote note = adminNoteMapper.toEntity(dto);
        note.setUser(user);
        note.setCreatedAt(LocalDateTime.now());

        AdminNote savedNote = adminNoteRepository.save(note);
        return adminNoteMapper.toDTO(savedNote);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminNoteResponseDTO> getNotesForUser(Long userId) {
        return adminNoteRepository.findByUserId(userId).stream()
                .map(adminNoteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminNoteResponseDTO> getAllNotes() {
        return adminNoteRepository.findAll().stream()
                .map(adminNoteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDTO getDashboardStats() {
        long totalUsers = userRepository.count();
        long activeUsers = userRepository.countByLastActiveDateGreaterThanEqual(LocalDate.now().minusDays(1));
        long totalModules = moduleRepository.count();
        long totalPuzzles = puzzleRepository.count();
        Long totalXp = userRepository.sumTotalXp();

        List<DashboardActivityDTO> recentActivities = new ArrayList<>();
        
        // Add recent registrations
        userRepository.findTop5ByOrderByCreatedAtDesc().forEach(user -> {
            recentActivities.add(new DashboardActivityDTO(
                "registration",
                user.getUsername(),
                "just joined the kingdom!",
                formatTimeAgo(user.getCreatedAt()),
                "person_add",
                false
            ));
        });

        // Mock some other activities if needed or fetch more real data
        // For now, let's keep it mostly real with registrations
        
        return new DashboardStatsDTO(
            totalUsers,
            activeUsers,
            totalModules,
            totalPuzzles,
            totalXp != null ? totalXp : 0L,
            recentActivities
        );
    }

    private String formatTimeAgo(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        java.time.Duration duration = java.time.Duration.between(dateTime, now);
        
        if (duration.toMinutes() < 60) {
            return duration.toMinutes() + " MINS AGO";
        } else if (duration.toHours() < 24) {
            return duration.toHours() + " HOURS AGO";
        } else {
            return duration.toDays() + " DAYS AGO";
        }
    }
}
