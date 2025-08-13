package br.com.jzsports.tournament_control.model.mapper;
import br.com.jzsports.tournament_control.model.dto.PlayerDTO;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.mapper.config.IgnoreImmutableConfig;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TeamMapper.class}, builder = @Builder(disableBuilder = true), config = IgnoreImmutableConfig.class)
public interface PlayerMapper {

    PlayerDTO toDto(Player player);
    Player toEntity(PlayerDTO player);
    void updatePlayer(PlayerDTO playerDTO, @MappingTarget Player player);

}
