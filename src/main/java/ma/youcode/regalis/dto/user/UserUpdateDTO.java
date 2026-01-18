package ma.youcode.regalis.dto.user;

import ma.youcode.regalis.enums.Role;

public record UserUpdateDTO(
        String email,
        String password,
        Role role) {
}
