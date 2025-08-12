package br.com.jzsports.tournament_control.model.mapper;

import br.com.jzsports.tournament_control.model.dto.MatchDTO;
import br.com.jzsports.tournament_control.model.entity.Team;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PlayerMapper.class, ChampionshipMapper.class}, builder = @Builder(disableBuilder = true))
public interface MatchMapper {
    MatchDTO toDto(Team team);
    Team toEntity(MatchDTO dto);
}
