package br.com.jzsports.tournament_control.model.mapper;

import br.com.jzsports.tournament_control.model.dto.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.entity.Championship;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PlayerMapper.class, TeamMapper.class}, builder = @Builder(disableBuilder = true))
public interface ChampionshipMapper {
    @Mapping(target = "createdBy", qualifiedByName = "toSimpleDto")
    ChampionshipDTO toDto(Championship championship);
    Championship toEntity(ChampionshipDTO championshipDTO);

}
