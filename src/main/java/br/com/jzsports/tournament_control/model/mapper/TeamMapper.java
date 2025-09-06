package br.com.jzsports.tournament_control.model.mapper;
import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamRequestDTO;
import br.com.jzsports.tournament_control.model.entity.Team;
import br.com.jzsports.tournament_control.model.mapper.config.IgnoreImmutableConfig;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), config = IgnoreImmutableConfig.class)
public interface TeamMapper {
    TeamDTO toDto(Team team);
    TeamRequestDTO toRequestDto(Team team);
    Team toEntity(TeamDTO teamDTO);

    void updateTeam(TeamRequestDTO teamRequestDTO, @MappingTarget Team team);
}
