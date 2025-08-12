package io.src.model.Network.DTO;

import io.src.model.Enums.GameLocationType;
import io.src.model.MapModule.GameLocations.Farm;

public class FarmMapper {
    public static FarmDTO toDTO(Farm farm) {
        if (farm == null) return null;
        FarmDTO dto = new FarmDTO();
        dto.setGameLocationType(farm.getType());
        dto.setFarnmapPath(farm.getFarnmapPath());
        dto.setPosition(farm.getPosition());
        dto.setPlayerUsername(farm.getPlayer() != null ? farm.getPlayer().getUser().getUsername() : null);
        dto.setDefaultHome(farm.getDefaultHome());
        return dto;
    }

    public static Farm fromDTO(FarmDTO dto) {
        if (dto == null) return null;
        Farm farm = new Farm(dto.getGameLocationType());
        farm.setFarnmapPath(dto.getFarnmapPath());
        farm.setPosition(dto.getPosition());
        return farm;
    }
}
