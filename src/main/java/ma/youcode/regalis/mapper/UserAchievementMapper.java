package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.userachievement.UserAchievementCreateDTO;
import ma.youcode.regalis.dto.userachievement.UserAchievementResponseDTO;
import ma.youcode.regalis.dto.userachievement.UserAchievementUpdateDTO;
import ma.youcode.regalis.entity.UserAchievement;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserAchievementMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "achievement", ignore = true)
    @Mapping(target = "unlockedDate", ignore = true)
    UserAchievement toEntity(UserAchievementCreateDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "achievement.id", target = "achievementId")
    UserAchievementResponseDTO toDTO(UserAchievement entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "achievement", ignore = true)
    void updateEntityFromDTO(UserAchievementUpdateDTO dto, @MappingTarget UserAchievement entity);
}
