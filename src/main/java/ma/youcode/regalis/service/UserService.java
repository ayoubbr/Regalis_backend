package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.user.UserCreateDTO;
import ma.youcode.regalis.dto.user.UserResponseDTO;
import ma.youcode.regalis.dto.user.UserUpdateDTO;
import ma.youcode.regalis.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserCreateDTO dto);

    UserResponseDTO getUserById(Long id);

    UserResponseDTO getUserByUsername(String username);

    List<UserResponseDTO> getAllUsers();

    Page<UserResponseDTO> getAllUsers(String search, Role role, Pageable pageable);

    UserResponseDTO updateUser(Long id, UserUpdateDTO dto);

    UserResponseDTO getCurrentUser();

    void deleteUser(Long id);
}
