package br.com.jzsports.tournament_control.model.mapper;
import br.com.jzsports.tournament_control.model.dto.TeamDTO;
import br.com.jzsports.tournament_control.model.entity.Team;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface TeamMapper {
    TeamDTO toDto(Team team);
    Team toEntity(TeamDTO teamDTO);
}
