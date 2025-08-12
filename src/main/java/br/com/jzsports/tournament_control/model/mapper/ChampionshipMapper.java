package br.com.jzsports.tournament_control.model.mapper;

import br.com.jzsports.tournament_control.model.dto.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.entity.Championship;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TeamMapper.class, MatchMapper.class}, builder = @Builder(disableBuilder = true))
public interface ChampionshipMapper {
    ChampionshipDTO toDto(Championship championship);
    Championship toEntity(ChampionshipDTO championshipDTO);
}
