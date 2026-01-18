package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.user.UserCreateDTO;
import ma.youcode.regalis.dto.user.UserResponseDTO;
import ma.youcode.regalis.dto.user.UserUpdateDTO;
import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserCreateDTO dto);

    UserResponseDTO getUserById(Long id);

    UserResponseDTO getUserByUsername(String username);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(Long id, UserUpdateDTO dto);

    void deleteUser(Long id);
}
