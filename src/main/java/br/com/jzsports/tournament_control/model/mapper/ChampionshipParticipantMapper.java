package br.com.jzsports.tournament_control.model.mapper;

import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipParticipantDTO;
import br.com.jzsports.tournament_control.model.entity.ChampionshipParticipant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TeamMapper.class})
public interface ChampionshipParticipantMapper {

    @Mapping(target = "team", source = "team")
    @Mapping(target = "championshipId", source = "championship.id")
    ChampionshipParticipantDTO toDto(ChampionshipParticipant participant);

    List<ChampionshipParticipantDTO> toDtoList(List<ChampionshipParticipant> participants);
}

