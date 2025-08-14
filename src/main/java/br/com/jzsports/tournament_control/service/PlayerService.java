package br.com.jzsports.tournament_control.service;

import br.com.jzsports.tournament_control.model.dto.PlayerDTO;
import br.com.jzsports.tournament_control.model.entity.Player;
import br.com.jzsports.tournament_control.model.mapper.PlayerMapper;
import br.com.jzsports.tournament_control.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    public PlayerDTO save(Player player) {
        boolean exists  = playerRepository.existsPlayerByEmailOrNickname(player.getEmail(), player.getNickname());
        if (exists) {
            throw new RuntimeException("Player already exists");
        }
        Player saved = playerRepository.save(player);
        return playerMapper.toDto(saved);
    }

    public PlayerDTO update(PlayerDTO playerDTO) {
        Player existing = playerRepository.findById(playerDTO.getId()).orElseThrow(() -> new RuntimeException("Player not found"));
        playerMapper.updatePlayer(playerDTO, existing);
        Player updated = playerRepository.save(existing);
        return playerMapper.toDto(updated);
    }

    public PlayerDTO findById(PlayerDTO id) {
        return playerRepository.findById(id.getId()).map(playerMapper::toDto).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public List<PlayerDTO> findAll() {
        List<Player> playersList = playerRepository.findAll();
        List<PlayerDTO> playersListDTO = playersList.stream().map(playerMapper::toDto).collect(Collectors.toList());
        Collections.reverse(playersListDTO);
        return playersListDTO;
    }

}
