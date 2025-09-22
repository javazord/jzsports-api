package br.com.jzsports.tournament_control.model.mapper;
import br.com.jzsports.tournament_control.model.dto.player.PlayerDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamDTO;
import br.com.jzsports.tournament_control.model.dto.team.TeamRequestDTO;
import br.com.jzsports.tournament_control.model.entity.Team;
import br.com.jzsports.tournament_control.model.entity.TeamMembership;
import br.com.jzsports.tournament_control.model.mapper.config.IgnoreImmutableConfig;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { TeamMembershipMapper.class }, builder = @Builder(disableBuilder = true), config = IgnoreImmutableConfig.class)
public interface TeamMapper {

    @Mapping(target = "players", expression = "java(toPlayerDtoList(team.getMemberships()))")
    TeamDTO toDto(Team team);

    List<TeamDTO> toDtoList(List<Team> teamList);

    TeamRequestDTO toRequestDto(Team team);

    Team toEntity(TeamDTO teamDTO);

    void updateTeam(TeamRequestDTO teamRequestDTO, @MappingTarget Team team);

    // 🔹 Transformar memberships → PlayerDTOs
    default List<PlayerDTO> toPlayerDtoList(List<TeamMembership> memberships) {
        if (memberships == null) return List.of();
        return memberships.stream()
                .map(TeamMembership::getPlayer)
                .map(player -> new PlayerDTO(
                        player.getId(),
                        player.getUsername(),
                        player.getNickname(),
                        player.getEmail(),
                        player.getColor(),
                        player.getPhotoURL()
                ))
                .toList();
    }

}
