package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.user.UserCreateDTO;
import ma.youcode.regalis.dto.user.UserResponseDTO;
import ma.youcode.regalis.dto.user.UserUpdateDTO;
import ma.youcode.regalis.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserCreateDTO dto);

    UserResponseDTO toDTO(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(UserUpdateDTO dto, @MappingTarget User entity);
}
