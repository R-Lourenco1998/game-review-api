package com.gamereview.api.services;

import com.gamereview.api.entities.Game;
import com.gamereview.api.entities.User;
import com.gamereview.api.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(Game game){
       return this.gameRepository.save(game);
    }

    public Game findGameById(Long id){
        Optional<Game> game = this.gameRepository.findById(id);
        return game.orElse(null);
    }

    public List<Game> findAllGames(){
        return this.gameRepository.findAll();
    }

    public Game updateGame(Long id, Game game){
        Game obj = findGameById(id);
        obj.setName(game.getName());
        obj.setGenre(game.getGenre());
        obj.setPlatform(game.getPlatform());
        obj.setReleaseDate(game.getReleaseDate());
        return this.gameRepository.save(game);
    }

    public void deleteGame(Long id){
        this.gameRepository.deleteById(id);
    }
}
