package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.adminnote.AdminNoteCreateDTO;
import ma.youcode.regalis.dto.adminnote.AdminNoteResponseDTO;
import java.util.List;

public interface AdminService {
    AdminNoteResponseDTO createAdminNote(AdminNoteCreateDTO dto);

    List<AdminNoteResponseDTO> getNotesForUser(Long userId);

    List<AdminNoteResponseDTO> getAllNotes();
}
