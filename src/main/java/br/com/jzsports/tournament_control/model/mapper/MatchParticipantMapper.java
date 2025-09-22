package br.com.jzsports.tournament_control.model.mapper;

import br.com.jzsports.tournament_control.model.dto.match.MatchParticipantDTO;
import br.com.jzsports.tournament_control.model.entity.MatchParticipant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TeamMapper.class, PlayerMapper.class})
public interface MatchParticipantMapper {

    @Mapping(target = "matchId", source = "match.id")
    @Mapping(target = "team", source = "team")
    @Mapping(target = "player", source = "player")
    MatchParticipantDTO toDto(MatchParticipant matchParticipant);

    List<MatchParticipantDTO> toDtoList(List<MatchParticipant> participants);
}
