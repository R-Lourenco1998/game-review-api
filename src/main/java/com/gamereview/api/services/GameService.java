package com.gamereview.api.services;
import com.gamereview.api.entities.Game;
import java.util.List;

public interface GameService {

    List<Game> findAllGames();

    Game createGame(Game game);

    Game findGameById(Long id);

    Game updateGame(Long id, Game game);

    void deleteGame(Long id);

    List<Game> findAllGamesDropdown();
}
