package io.src.model.Network.DTO;

import io.src.model.Game;

import java.util.stream.Collectors;

public class GameMapper {
    public static GameDTO toDTO(Game game) {
        GameDTO dto = new GameDTO();
//        dto.setGameId(game.getGameId());
        dto.setPlayers(game.getPlayers().stream()
            .map(PlayerMapper::toDTO)
            .collect(Collectors.toList()));
        dto.setGameMap(GameMapMapper.toDTO(game.getGameMap()));
        dto.setTimeSystem(TimeSystemMapper.toDTO(game.getTimeSystem()));
        dto.setWeatherState(WeatherMapper.toDTO(game.getWeatherState()));
        dto.setStarterPlayerUserId(game.getStarterPlayer() != null ? game.getStarterPlayer().getUser().getUsername() : null);
        dto.setCurrentPlayerUserId(game.getCurrentPlayer() != null ? game.getCurrentPlayer().getUser().getUsername() : null);
        return dto;
    }

    public static Game fromDTO(GameDTO dto) {
        Game game = new Game(null, null, null, null);
        game.setGameId(dto.getGameId());
        game.setPlayers((java.util.ArrayList<io.src.model.Player>) dto.getPlayers().stream()
            .map(PlayerMapper::fromDTO)
            .collect(Collectors.toList()));
        game.setGameMap(GameMapMapper.fromDTO(dto.getGameMap()));
        game.setTimeSystem(TimeSystemMapper.fromDTO(dto.getTimeSystem()));
        game.setWeatherState(WeatherMapper.fromDTO(dto.getWeatherState()));
        return game;
    }
}

