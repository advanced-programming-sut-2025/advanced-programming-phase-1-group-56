package io.src.model.Network.DTO;

import io.src.model.Player;
import io.src.model.User;

import java.util.stream.Collectors;

public class PlayerMapper {
    public static PlayerDTO toDTO(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setUsername(player.getUser().getUsername());
        dto.setFarmPosition(player.getFarmPosition());
        dto.setGold(player.getGold());
        return dto;
    }

    public static Player fromDTO(PlayerDTO dto) {
        User user = new User(dto.getUsername(),null,null,null,null,0,null,null);
        Player player = new Player(user);
        player.setFarmPosition(dto.getFarmPosition());

        return player;
    }
}
