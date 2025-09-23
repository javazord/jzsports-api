package br.com.jzsports.tournament_control.model.mapper;
import br.com.jzsports.tournament_control.model.dto.phase.PhaseDTO;
import br.com.jzsports.tournament_control.model.dto.phase.PhaseResponseDTO;
import br.com.jzsports.tournament_control.model.entity.Match;
import br.com.jzsports.tournament_control.model.entity.Phase;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MatchMapper.class})
public interface PhaseMapper {

    @Mapping(target = "championshipId", source = "championship.id")
    @Mapping(target = "phaseType", source = "phaseType.description")
    @Mapping(target = "matchesId", source = "matches")
    PhaseDTO toDto(Phase phase);

    @Mapping(target = "championshipName", source = "championship.championshipName")
    @Mapping(target = "championshipId", source = "championship.id")
    @Mapping(target = "phaseType", source = "phaseType.description")
    PhaseResponseDTO toResponseDto(Phase phase);

    List<PhaseDTO> toDtoList(List<Phase> phases);

    // 🔹 Método auxiliar para converter Matches → IDs
    default List<Long> mapMatchesToIds(List<Match> matches) {
        if (matches == null) {
            return List.of();
        }
        return matches.stream()
                .map(Match::getId)
                .toList();
    }
}
