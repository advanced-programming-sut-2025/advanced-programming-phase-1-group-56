package io.src.model.Network.DTO;

import io.src.model.States.WeatherState;

public class WeatherMapper {
    public static WeatherStateDTO toDTO(WeatherState ws) {
        WeatherStateDTO dto = new WeatherStateDTO();
        dto.setCurrentWeather(ws.getCurrentWeather());
        return dto;
    }

    public static WeatherState fromDTO(WeatherStateDTO dto) {
        WeatherState ws = new WeatherState();
        ws.setTodayWeather(dto.getCurrentWeather());
        return ws;
    }
}
