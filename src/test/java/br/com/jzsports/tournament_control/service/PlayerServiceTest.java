package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.PlayerDTO;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.mapper.PlayerMapper;
import br.com.jzsports.tournament_control.repository.PlayerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerMapper playerMapper;

    @InjectMocks
    private PlayerService playerService;

    private Player player;
    private PlayerDTO playerDTO;
    @BeforeEach
    void setUp() {
        player = createPlayer();
        // inicializa playerDTO manualmente
        playerDTO = new PlayerDTO();
        playerDTO.setId(1L);
        playerDTO.setName(player.getPlayerName());
        playerDTO.setNickname(player.getNickname());
        playerDTO.setEmail(player.getEmail());
        playerDTO.setPhotoURL(player.getPhotoURL());
    }

    // ===================== CREATE =====================
    @Test
    @DisplayName("Should save player with successful")
    void saveCase1() {
        // simula que NÃO existe player ainda
        when(playerRepository.existsPlayerByEmailOrNickname(anyString(), anyString())).thenReturn(false);
        // simula persistência
        when(playerRepository.save(any(Player.class))).thenReturn(player);
        // simula conversão para DTO
        when(playerMapper.toDto(any(Player.class))).thenReturn(playerDTO);

        PlayerDTO result = playerService.save(player);

        Assertions.assertNotNull(result);
        assertThat(result.getEmail()).isEqualTo("mateus@gmail.com");

        verify(playerRepository).existsPlayerByEmailOrNickname(player.getEmail(), player.getNickname());
        verify(playerRepository).save(player);
        verify(playerMapper).toDto(player);
    }

    @Test
    @DisplayName("Not should save player because it already exists")
    void saveCase2() {
        // simula que player já existe
        when(playerRepository.existsPlayerByEmailOrNickname(anyString(), anyString())).thenReturn(true);

        Exception thrown = Assertions.assertThrows(RuntimeException.class, () -> playerService.save(player));

        Assertions.assertEquals("Player already exists", thrown.getMessage());
    }

    // ===================== UPDATE =====================
    @Test
    @DisplayName("Should update player with successful")
    void updateCase1() {
        when(playerRepository.findById(playerDTO.getId())).thenReturn(Optional.of(player));
        doAnswer(invocation -> {
            PlayerDTO dto = invocation.getArgument(0);
            Player entity = invocation.getArgument(1);
            entity.setPlayerName(dto.getName());
            return null;
        }).when(playerMapper).updatePlayer(any(PlayerDTO.class), any(Player.class));
        when(playerRepository.save(player)).thenReturn(player);
        when(playerMapper.toDto(player)).thenReturn(playerDTO);
        Long id = 1L;
        PlayerDTO result = playerService.update(id, playerDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Mateus", result.getName());
        verify(playerRepository).findById(playerDTO.getId());
        verify(playerMapper).updatePlayer(playerDTO, player);
        verify(playerRepository).save(player);
        verify(playerMapper).toDto(player);
    }

    @Test
    @DisplayName("Not should update player with successful, player already exists")
    void updateCase2() {
        when(playerRepository.findById(playerDTO.getId())).thenReturn(Optional.empty());
        Long id = 1L;
        Assertions.assertThrows(RuntimeException.class, () -> playerService.update(id, playerDTO));

        verify(playerRepository).findById(playerDTO.getId());
        verifyNoMoreInteractions(playerMapper, playerRepository);
    }

    // ===================== FIND BY ID =====================
    @Test
    @DisplayName("Should find player with successful")
    void findByIdCase1() {
        when(playerRepository.findById(playerDTO.getId())).thenReturn(Optional.of(player));
        when(playerMapper.toDto(player)).thenReturn(playerDTO);

        PlayerDTO result = playerService.findById(playerDTO.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(playerDTO.getId(), result.getId());
        verify(playerRepository).findById(playerDTO.getId());
        verify(playerMapper).toDto(player);
    }

    @Test
    @DisplayName("Not should find player with successful, player not found")
    void findByIdCase2() {
        when(playerRepository.findById(playerDTO.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> playerService.findById(playerDTO.getId()));

        verify(playerRepository).findById(playerDTO.getId());
        verifyNoInteractions(playerMapper);
    }

    // ===================== FIND ALL =====================
    @Test
    @DisplayName("Should find all players with successful")
    void findAllCase1() {
        List<Player> players = List.of(player);

        when(playerRepository.findAll()).thenReturn(players);
        when(playerMapper.toDto(player)).thenReturn(playerDTO);

        List<PlayerDTO> result = playerService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(playerDTO, result.get(0));
        verify(playerRepository).findAll();
        verify(playerMapper).toDto(player);
    }

    @Test
    @DisplayName("Not should find all players with successful, empty list")
    void findAllCase2() {
        when(playerRepository.findAll()).thenReturn(Collections.emptyList());

        List<PlayerDTO> result = playerService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
        verify(playerRepository).findAll();
        verifyNoInteractions(playerMapper);
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

}