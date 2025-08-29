package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.TeamDTO;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.entity.Team;
import br.com.jzsports.tournament_control.model.mapper.TeamMapper;
import br.com.jzsports.tournament_control.repository.PlayerRepository;
import br.com.jzsports.tournament_control.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamMapper teamMapper;

    @InjectMocks
    private TeamService teamService;

    private Team team;
    private TeamDTO teamDTO;

    @BeforeEach
    void setUp() {
        team = createTeam();
        teamDTO = createTeamDTO();
    }

    @Test
    @DisplayName("Should save team in database")
    void saveCase01() {
        Team team = createTeam();

        List<Long> playerIds = List.of(1L, 2L);
        List<Player> players = List.of(createPlayer(), createPlayer());

        when(playerRepository.findAllById(playerIds)).thenReturn(players);
        when(teamRepository.save(team)).thenReturn(team);
        when(teamMapper.toDto(team)).thenReturn(teamDTO);

        TeamDTO result = teamService.save(team, playerIds);

        assertNotNull(result);
        assertEquals(teamDTO.getTeamName(), result.getTeamName());
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    @DisplayName("Not should save team in database")
    void saveCase02() {
        assertThrows(IllegalArgumentException.class, () -> teamService.save(team, List.of()));
        verify(teamRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find team in database")
    void findByIdCase01() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(teamMapper.toDto(team)).thenReturn(teamDTO);
        TeamDTO result = teamService.findById(1L);
        assertNotNull(result);
        assertEquals("TeamOne", result.getTeamName());
        verify(teamRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Not should find team in database")
    void findByIdCase02() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teamService.findById(1L));
        verify(teamRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should find all team in database")
    void findAllTeamsCase01() {
        when(teamRepository.findAll()).thenReturn(List.of(team));
        when(teamMapper.toDto(team)).thenReturn(teamDTO);
        List<TeamDTO> result = teamService.findAllByNameAndPlayersList_Id(team.getTeamName(), 1L);
        assertNotNull(result);
        assertEquals(teamDTO.getTeamName(), result.get(0).getTeamName());
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Not should find all team in database")
    void findAllTeamsCase02() {
        when(teamRepository.findAll()).thenReturn(List.of());
        List<TeamDTO> result = teamService.findAllByNameAndPlayersList_Id(team.getTeamName(), 1L);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Not should update team in database")
    void updateCase01() {
        List<Long> playerIds = List.of(1L, 2L);
        List<Player> players = List.of(createPlayer(), createPlayer());

        when(teamRepository.findById(teamDTO.getId())).thenReturn(Optional.of(team));
        when(playerRepository.findAllById(playerIds)).thenReturn((players));
        when(teamRepository.save(team)).thenReturn(team);
        when(teamMapper.toDto(team)).thenReturn(teamDTO);

        TeamDTO result = teamService.update(teamDTO, playerIds);

        assertNotNull(result);
        assertEquals(teamDTO.getTeamName(), result.getTeamName());
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    @DisplayName("Not should update team in database")
    void updateCase02() {

        List<Long> playerIds = List.of(1L, 2L);
        when(teamRepository.findById(teamDTO.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teamService.update(teamDTO, playerIds));
        verify(teamRepository, never()).save(any());

    }

    private Team createTeam() {
        return new Team(null, "TeamOne", null, null, null, null);
    }

    private TeamDTO createTeamDTO() {
        return new TeamDTO(1L, "TeamOne", null, null, null, null);
    }

    private Player createPlayer() {
        return new Player(
                null,
                "Mateus",
                "javazord",
                "123@",
                "mateus@gmail.com",
                "undefined",
                LocalDateTime.now(),
                null,
                null
        );
    }

}