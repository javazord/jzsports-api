package br.com.jzsports.tournament_control.model.mapper;
import br.com.jzsports.tournament_control.model.dto.PhaseDTO;
import br.com.jzsports.tournament_control.model.entity.Phase;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TeamMapper.class}, builder = @Builder(disableBuilder = true))
public interface PhaseMapper {
    PhaseDTO toDto(Phase phase);
    Phase toEntity(PhaseDTO phaseDTO);
}
