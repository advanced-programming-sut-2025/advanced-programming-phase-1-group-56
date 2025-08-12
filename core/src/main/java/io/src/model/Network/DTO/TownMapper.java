package io.src.model.Network.DTO;

import io.src.model.Enums.GameLocationType;
import io.src.model.MapModule.GameLocations.Town;
import io.src.model.Network.DTO.TownDTO;

import java.util.stream.Collectors;

public class TownMapper {
    public static TownDTO toDTO(Town town) {
        if (town == null) return null;
        TownDTO dto = new TownDTO();
        dto.setTownmapPath(town.getTownmapPath());

        return dto;
    }

    public static Town fromDTO(TownDTO dto) {
        if (dto == null) return null;
        Town town = new Town(GameLocationType.Town);
        town.setTownmapPath(dto.getTownmapPath());
        return town;
    }
}
