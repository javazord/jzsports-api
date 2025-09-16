package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.championship.ChampionshipDTO;
import br.com.jzsports.tournament_control.model.e.ETypeChampionship;
import br.com.jzsports.tournament_control.model.entity.Championship;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.mapper.ChampionshipMapper;
import br.com.jzsports.tournament_control.repository.ChampionshipRepository;
import br.com.jzsports.tournament_control.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ChampionshipServiceTest {

    @Mock
    private ChampionshipRepository championshipRepository;

    @Mock
    private ChampionshipMapper championshipMapper;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private ChampionshipService championshipService;

    private Player player;
    private Championship championship;
    private ChampionshipDTO championshipDTO;

    @BeforeEach
    void setUp() {

        player = new Player();
        player.setId(1L);
        player.setUsername(("Player Test"));

        championship = new Championship();
        championship.setId(10L);
        championship.setChampionshipName("Championship Test");
        championship.setCreatedBy(player);

        championshipDTO = new ChampionshipDTO();
        championshipDTO.setId(10L);
        championshipDTO.setChampionshipName("Championship DTO");
        championshipDTO.setChampionshipType(ETypeChampionship.FPS);
        championshipDTO.setStartDate(LocalDate.of(2025, 1, 1));
        championshipDTO.setEndDate(LocalDate.of(2025, 1, 10));
    }

    // ---------- SAVE ----------
    @Test
    @DisplayName("Should save with successful")
    void saveCase01() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(championshipMapper.toDto(any(Championship.class))).thenReturn(championshipDTO);

        ChampionshipDTO result = championshipService.save(championship);

        assertThat(result).isNotNull();
        assertThat(result.getChampionshipName()).isEqualTo("Championship DTO");
        verify(playerRepository).findById(1L);
        verify(championshipMapper).toDto(championship);
    }

    @Test
    @DisplayName("Not should if player is not found")
    void saveCase02() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> championshipService.save(championship))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Player not found");

        verify(playerRepository).findById(1L);
        verifyNoInteractions(championshipMapper);
    }

    // ---------- UPDATE ----------
    @Test
    @DisplayName("Should update if is same player")
    void updateCase01() {
        when(championshipRepository.findById(10L)).thenReturn(Optional.of(championship));
        when(championshipRepository.save(any(Championship.class))).thenReturn(championship);

        ChampionshipDTO result = championshipService.updateChampionship(10L, player, championshipDTO);

        assertThat(result.getChampionshipName()).isEqualTo("Championship DTO");
        assertThat(result.getChampionshipType()).isEqualTo("Knockout");
        assertThat(result.getStartDate()).isEqualTo(LocalDate.of(2025, 1, 1));
        verify(championshipRepository).save(championship);
    }

    @Test
    @DisplayName("Not should update championship if is same not exists")
    void updateCase02() {
        when(championshipRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> championshipService.updateChampionship(10L, player, championshipDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Championship not found");

        verify(championshipRepository).findById(10L);
        verify(championshipRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should launch exception if other player try update")
    void updateCase03() {
        Player outroPlayer = new Player();
        outroPlayer.setId(99L);

        when(championshipRepository.findById(10L)).thenReturn(Optional.of(championship));

        assertThatThrownBy(() -> championshipService.updateChampionship(10L, outroPlayer, championshipDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("do not have permission");

        verify(championshipRepository, never()).save(any());
    }

    // ---------- GET BY PLAYER ----------
    @Test
    @DisplayName("Should get championship by player")
    void getChampionshipsByPlayer() {
        when(championshipRepository.findByCreatedBy_Id(1L)).thenReturn(List.of(championshipDTO));

        List<ChampionshipDTO> result = championshipService.getChampionshipsByCreatedBy(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getChampionshipName()).isEqualTo("Championship Test");
        verify(championshipRepository).findByCreatedBy_Id(1L);
    }
}