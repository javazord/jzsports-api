package br.com.jzsports.tournament_control.model.mapper;

import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.entity.Championship;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PlayerMapper.class}, builder = @Builder(disableBuilder = true))
public interface ChampionshipMapper {

    @Mapping(target = "createdBy", qualifiedByName = "toSimpleDto")
    ChampionshipDTO toDto(Championship championship);

    Championship toEntity(ChampionshipDTO championshipDTO);

    List<ChampionshipDTO> toDtoList(List<Championship> championship);

    @AfterMapping
    default void fillPhotoUrl(Championship championship, @MappingTarget ChampionshipDTO dto) {
        if (championship.getCreatedBy() != null && dto.getCreatedBy() != null) {
            dto.getCreatedBy().setPhotoURL(championship.getCreatedBy().getPhotoURL());
        }
    }
}
