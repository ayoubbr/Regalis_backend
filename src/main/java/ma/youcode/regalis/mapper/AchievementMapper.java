package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.achievement.AchievementCreateDTO;
import ma.youcode.regalis.dto.achievement.AchievementResponseDTO;
import ma.youcode.regalis.dto.achievement.AchievementUpdateDTO;
import ma.youcode.regalis.entity.Achievement;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AchievementMapper {
    @Mapping(target = "userAchievements", ignore = true)
    Achievement toEntity(AchievementCreateDTO dto);

    AchievementResponseDTO toDTO(Achievement entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userAchievements", ignore = true)
    void updateEntityFromDTO(AchievementUpdateDTO dto, @MappingTarget Achievement entity);
}
