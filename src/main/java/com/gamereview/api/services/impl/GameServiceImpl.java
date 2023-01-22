package com.gamereview.api.services.impl;

import com.gamereview.api.entities.Game;
import com.gamereview.api.repositories.GameRepository;
import com.gamereview.api.services.GameService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(Game game) {
        return this.gameRepository.save(game);
    }

    public List<Game> findAllGamesDropdown() {
        return this.gameRepository.findAll();
    }

    public Game findGameById(Long id) {
        Optional<Game> game = this.gameRepository.findById(id);
        return game.orElse(null);
    }

    public List<Game> findAllGames() {
        return this.gameRepository.findAll();
    }

    public Game updateGame(Long id, Game game) {
        Game obj = findGameById(id);
        obj.setName(game.getName());
        obj.setDescription(game.getDescription());
        obj.setGenre(game.getGenre());
        obj.setPlatform(game.getPlatform());
        obj.setReleaseDate(game.getReleaseDate());
        obj.setDeveloper(game.getDeveloper());
        obj.setPublisher(game.getPublisher());
        return this.gameRepository.save(game);
    }

    public void deleteGame(Long id) {
        this.gameRepository.deleteById(id);
    }
}
