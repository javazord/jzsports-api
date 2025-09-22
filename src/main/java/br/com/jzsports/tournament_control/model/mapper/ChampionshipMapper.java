package br.com.jzsports.tournament_control.model.mapper;

import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.ChampionshipParticipant;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {TeamMapper.class})
public interface ChampionshipMapper {

    @Mapping(target = "createdByPlayerId", source = "createdBy.id")
    @Mapping(target = "championshipType", source = "championshipType") // ENUM → String
    @Mapping(target = "championshipStatus", source = "championshipStatus") // ENUM → String
    ChampionshipDTO toDto(Championship championship);

    @Mapping(target = "participants", ignore = true)
    Championship toEntity(ChampionshipDTO dto);

    List<ChampionshipDTO> toDtoList(List<Championship> championships);

    // 🔹 Auxiliar: ChampionshipParticipant → TeamDTO
    default List<br.com.jzsports.tournament_control.model.dto.team.TeamDTO> toTeamDtoList(List<ChampionshipParticipant> participants) {
        if (participants == null) return List.of();
        return participants.stream()
                .map(ChampionshipParticipant::getTeam)
                .map(team -> new TeamDTO(
                        team.getId(),
                        team.getTeamName(),
                        team.getPhotoURL(),
                        team.getCreatedAt(),
                        null // players vão ser preenchidos via TeamMapper
                ))
                .collect(Collectors.toList());
    }
}
