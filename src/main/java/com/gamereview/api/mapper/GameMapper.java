package com.gamereview.api.mapper;
import com.gamereview.api.entities.Game;
import com.gamereview.api.entities.dto.GameDTO;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring", uses = {Game.class, GameDTO.class})
public interface GameMapper {

    Game toEntity(GameDTO gameDTO);
    GameDTO toDTO(Game game);
}
