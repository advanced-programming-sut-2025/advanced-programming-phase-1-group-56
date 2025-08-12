package io.src.model.Network.DTO;

import io.src.model.TimeSystem.TimeSystem;

public class TimeSystemMapper {
    public static TimeSystemDTO toDTO(TimeSystem timeSystem) {
        TimeSystemDTO dto = new TimeSystemDTO();
        dto.setDay(timeSystem.getDateTime().getDay());
        dto.setHour(timeSystem.getDateTime().getHour());
        return dto;
    }

    public static TimeSystem fromDTO(TimeSystemDTO dto) {
        return new TimeSystem(dto.getDay(), dto.getHour());
    }
}
