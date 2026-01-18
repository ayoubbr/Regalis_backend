package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.adminnote.AdminNoteCreateDTO;
import ma.youcode.regalis.dto.adminnote.AdminNoteResponseDTO;
import ma.youcode.regalis.entity.AdminNote;
import ma.youcode.regalis.entity.User;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.AdminNoteMapper;
import ma.youcode.regalis.repository.AdminNoteRepository;
import ma.youcode.regalis.repository.UserRepository;
import ma.youcode.regalis.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminNoteRepository adminNoteRepository;
    private final UserRepository userRepository;
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
}
