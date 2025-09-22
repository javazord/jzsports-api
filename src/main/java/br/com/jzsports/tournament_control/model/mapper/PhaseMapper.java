package br.com.jzsports.tournament_control.model.mapper;
import br.com.jzsports.tournament_control.model.dto.phase.PhaseDTO;
import br.com.jzsports.tournament_control.model.entity.Phase;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MatchMapper.class})
public interface PhaseMapper {

    @Mapping(target = "championshipId", source = "championship.id")
    @Mapping(target = "matches", source = "matches")
    PhaseDTO toDto(Phase phase);

    List<PhaseDTO> toDtoList(List<Phase> phases);
}
