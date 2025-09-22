package br.com.jzsports.tournament_control.model.mapper;

import br.com.jzsports.tournament_control.model.dto.team.TeamMembershipDTO;
import br.com.jzsports.tournament_control.model.entity.TeamMembership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { PlayerMapper.class})
public interface TeamMembershipMapper {

    @Mapping(target = "team", ignore = true)
    @Mapping(target = "player", source = "player")
    TeamMembershipDTO toDto(TeamMembership teamMembership);

    List<TeamMembershipDTO> toDtoList(List<TeamMembership> memberships);
}
