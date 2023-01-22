package com.gamereview.api.rest.controller;

import com.gamereview.api.entities.Game;
import com.gamereview.api.entities.dto.GameDropdownDTO;
import com.gamereview.api.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins ="http://localhost:4200")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    @Operation(tags = {"Game"}, summary = "Find all games", description = "Find all games")
    public List<Game> findAll() {
        return gameService.findAllGames();
    }

    @GetMapping("/list")
    @Operation(tags = {"Game"}, summary = "Find all games for the dropdown", description = "Find all games for the dropdown")
    public List<GameDropdownDTO> findAllGamesDropdown() {

        return gameService.findAllGamesDropdown();
    }

    @GetMapping("/{id}")
    @Operation(tags = {"Game"}, summary = "Find game by id", description = "Find game by id")
    public ResponseEntity<Game> findGameById(@PathVariable Long id){
        Game game = gameService.findGameById(id);
        if(game == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(game);
    }

    @PostMapping
    @Operation(tags = {"Game"}, summary = "Create game", description = "Create game")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Game> createGame(@RequestBody Game game){
        game = gameService.createGame(game);
        return ResponseEntity.ok().body(game);
    }

    @PutMapping("/{id}")
    @Operation(tags = {"Game"}, summary = "Update game", description = "Update game by id and body")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game game){
        if(gameService.findGameById(id) == null){
            return ResponseEntity.notFound().build();
        }else{
            game = gameService.updateGame(id, game);
            return ResponseEntity.ok().body(game);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(tags = {"Game"}, summary = "Delete game", description = "Delete game by id")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id){
        if(gameService.findGameById(id) == null){
            return ResponseEntity.notFound().build();
        }else{
            gameService.deleteGame(id);
            return ResponseEntity.noContent().build();
        }
    }

}
