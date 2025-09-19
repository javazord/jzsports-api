package br.com.jzsports.tournament_control.model.mapper;

import br.com.jzsports.tournament_control.model.dto.match.MatchDTO;
import br.com.jzsports.tournament_control.model.entity.Match;
import br.com.jzsports.tournament_control.model.entity.Team;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PlayerMapper.class, ChampionshipMapper.class}, builder = @Builder(disableBuilder = true))
public interface MatchMapper {

    @Mapping(target = "statusDescription", source = "status.description")
    MatchDTO toDto(Match match);

    Match toEntity(MatchDTO dto);

}
