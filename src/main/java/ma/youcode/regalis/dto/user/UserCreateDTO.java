package ma.youcode.regalis.dto.user;

import ma.youcode.regalis.enums.Role;

public record UserCreateDTO(
        String username,
        String email,
        String password,
        Role role) {
}
