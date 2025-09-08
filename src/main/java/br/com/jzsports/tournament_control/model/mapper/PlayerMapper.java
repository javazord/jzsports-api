package br.com.jzsports.tournament_control.model.mapper;
import br.com.jzsports.tournament_control.model.dto.player.PlayerDTO;
import br.com.jzsports.tournament_control.model.dto.player.PlayerProfDTO;
import br.com.jzsports.tournament_control.model.dto.player.PlayerSimpleDTO;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.mapper.config.IgnoreImmutableConfig;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), config = IgnoreImmutableConfig.class)
public interface PlayerMapper {

    PlayerDTO toDto(Player player);
    Player toEntity(PlayerDTO playerDTO);

    @Mapping(target = "createdAt", ignore = true)
    void updatePlayer(PlayerDTO playerDTO, @MappingTarget Player player);

    @Mapping(target = "createdAt", ignore = true)
    void updatePlayerProfile(PlayerProfDTO playerProfDTO, @MappingTarget Player player);

    @Named("toSimpleDto")
    PlayerSimpleDTO toSimpleDto(Player player);

}
