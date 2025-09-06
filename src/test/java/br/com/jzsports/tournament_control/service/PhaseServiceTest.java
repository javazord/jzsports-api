package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.phase.PhaseDTO;
import br.com.jzsports.tournament_control.model.e.EMatchStatus;
import br.com.jzsports.tournament_control.model.e.ETypePhase;
import br.com.jzsports.tournament_control.model.entity.*;
import br.com.jzsports.tournament_control.model.mapper.PhaseMapper;
import br.com.jzsports.tournament_control.repository.ChampionshipRepository;
import br.com.jzsports.tournament_control.repository.PhaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PhaseServiceTest {

    @Mock
    private ChampionshipRepository championshipRepository;

    @Mock
    private PhaseRepository phaseRepository;

    @Mock
    private MatchService matchService;

    @Mock
    private PhaseMapper phaseMapper;

    @InjectMocks
    private PhaseService phaseService;

    private Player player;
    private Championship championship;
    private Team teamA, teamB;
    private Match match;
    private Phase currentPhase;

    @BeforeEach
    void setUp() {
        player = createPlayer();
        championship = createChampionship();

        teamA = new Team();
        teamA.setId(1L);
        teamA.setTeamName("Team A");

        teamB = new Team();
        teamB.setId(2L);
        teamB.setTeamName("Team B");

        match = createMatch();

        currentPhase = getCurrentPhase();
    }

    @Test
    @DisplayName("Should generate next phase when all matches is finished")
    void generateNextPhaseCase01() {

        currentPhase.setMatchesList(List.of(match));

        when(championshipRepository.findById(1L)).thenReturn(Optional.of(championship));
        when(phaseRepository.findByChampionship_IdAndPhase(1L, ETypePhase.QUARTER_FINAL))
                .thenReturn(List.of(currentPhase));
        when(matchService.getWinner(match)).thenReturn(teamA);
        when(phaseRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(matchService.createMatchesForPhase(any(), any())).thenReturn(List.of());

        phaseService.generateNextPhase(1L, ETypePhase.QUARTER_FINAL);

        verify(phaseRepository, times(2)).save(any(Phase.class));
        verify(matchService).createMatchesForPhase(any(Phase.class), anyList());
    }

    @Test
    @DisplayName("Not should generate next phase if all matches not finished")
    void generateNextPhaseCase02() {
        Match match = new Match();
        match.setPhase(currentPhase);
        match.setStatus(EMatchStatus.IN_PROGRESS);
        currentPhase.setMatchesList(List.of(match));

        when(championshipRepository.findById(1L)).thenReturn(Optional.of(championship));
        when(phaseRepository.findByChampionship_IdAndPhase(1L, ETypePhase.QUARTER_FINAL))
                .thenReturn(List.of(currentPhase));

        assertThatThrownBy(() -> phaseService.generateNextPhase(1L, ETypePhase.QUARTER_FINAL))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Not all matches are finished or cancelled");
    }

    @Test
    @DisplayName("Should launch exception when not exists next phase")
    void generateNextPhaseCase03(){
        currentPhase.setMatchesList(List.of());

        when(championshipRepository.findById(1L)).thenReturn(Optional.of(championship));
        when(phaseRepository.findByChampionship_IdAndPhase(1L, ETypePhase.FINAL))
                .thenReturn(List.of(currentPhase));

        // seta a fase final, que já é naturalmente "última"
        currentPhase.setPhase(ETypePhase.FINAL);

        assertThatThrownBy(() -> phaseService.generateNextPhase(1L, ETypePhase.FINAL))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No next phase");
    }

    @Test
    @DisplayName("Should find phase by id")
    void findById() {
        currentPhase.setId(99L);
        when(phaseRepository.findById(99L)).thenReturn(Optional.of(currentPhase));

        PhaseDTO dto = new PhaseDTO();
        when(phaseMapper.toDto(currentPhase)).thenReturn(dto);

        PhaseDTO result = phaseService.findById(99L);

        assertThat(result).isNotNull();
        verify(phaseMapper).toDto(currentPhase);
    }

    private Player createPlayer() {
        return new Player(
                null,
                "Mateus",
                "javazord",
                "123@",
                "mateus@gmail.com",
                "",
                "undefined",
                LocalDateTime.now(),
                null,
                null
        );
    }

    public Championship createChampionship() {
        return new Championship(null, "Championship Test", "Fifa25", LocalDate.now(), null, null, null, player);
    }

    public Match createMatch() {
        return new Match(null, 2, 1, championship, teamA, teamB, EMatchStatus.FINISHED, currentPhase, null);
    }

    public Phase getCurrentPhase() {
        return new Phase(null, ETypePhase.QUARTER_FINAL, teamA, championship, null);
    }

}