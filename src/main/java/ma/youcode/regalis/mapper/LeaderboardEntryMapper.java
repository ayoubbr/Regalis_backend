package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.leaderboardentry.LeaderboardEntryCreateDTO;
import ma.youcode.regalis.dto.leaderboardentry.LeaderboardEntryResponseDTO;
import ma.youcode.regalis.dto.leaderboardentry.LeaderboardEntryUpdateDTO;
import ma.youcode.regalis.entity.LeaderboardEntry;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LeaderboardEntryMapper {
    @Mapping(target = "user", ignore = true)
    LeaderboardEntry toEntity(LeaderboardEntryCreateDTO dto);

    @Mapping(source = "user.id", target = "userId")
    LeaderboardEntryResponseDTO toDTO(LeaderboardEntry entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    void updateEntityFromDTO(LeaderboardEntryUpdateDTO dto, @MappingTarget LeaderboardEntry entity);
}
