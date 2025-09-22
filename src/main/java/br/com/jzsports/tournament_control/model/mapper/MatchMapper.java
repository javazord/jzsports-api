package br.com.jzsports.tournament_control.model.mapper;

import br.com.jzsports.tournament_control.model.dto.match.MatchDTO;
import br.com.jzsports.tournament_control.model.entity.Match;
import br.com.jzsports.tournament_control.model.entity.Team;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MatchParticipantMapper.class})
public interface MatchMapper {

    @Mapping(target = "phaseId", source = "phase.id")
    @Mapping(target = "participants", source = "participants")
    MatchDTO toDto(Match match);

    List<MatchDTO> toDtoList(List<Match> matches);
}
