package br.com.jzsports.tournament_control.model.mapper;
import br.com.jzsports.tournament_control.model.dto.PlayerDTO;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.entity.Team;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TeamMapper.class}, builder = @Builder(disableBuilder = true))
public interface PlayerMapper {
    PlayerDTO toDto(Team team);
    Player toEntity(PlayerDTO dto);
}
