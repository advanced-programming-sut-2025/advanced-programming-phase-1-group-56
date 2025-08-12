package io.src.model.Network.DTO;

import io.src.model.MapModule.GameMap;

public class GameMapMapper {
    public static GameMapDTO toDTO(GameMap map) {
        GameMapDTO dto = new GameMapDTO();
        dto.setPelikanTown(TownMapper.toDTO(map.getPelikanTown()));
        dto.setFarm1(FarmMapper.toDTO(map.getFarm1()));
        dto.setFarm2(FarmMapper.toDTO(map.getFarm2()));
        dto.setFarm3(FarmMapper.toDTO(map.getFarm3()));
        dto.setFarm4(FarmMapper.toDTO(map.getFarm4()));
        return dto;
    }

    public static GameMap fromDTO(GameMapDTO dto) {
        GameMap map = new GameMap();
        map.setPelikanTown(TownMapper.fromDTO(dto.getPelikanTown()));
        map.setFarm1(FarmMapper.fromDTO(dto.getFarm1()));
        map.setFarm2(FarmMapper.fromDTO(dto.getFarm2()));
        map.setFarm3(FarmMapper.fromDTO(dto.getFarm3()));
        map.setFarm4(FarmMapper.fromDTO(dto.getFarm4()));
        return map;
    }
}
