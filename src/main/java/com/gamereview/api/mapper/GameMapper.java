package com.gamereview.api.mapper;

import com.gamereview.api.entities.Game;
import com.gamereview.api.entities.dto.GameDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = { Game.class, GameDTO.class })
public interface GameMapper {

    Game toEntity(GameDTO gameDTO);

    GameDTO toDTO(Game game);

    GameDTO toDTOOptional(Optional<Game> game);

    List<GameDTO> gamesListToDTO(List<Game> gameList);

    List<Game> gamesListToEntity(List<GameDTO> gameDTOList);
}
