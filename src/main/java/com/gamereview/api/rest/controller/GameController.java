package com.gamereview.api.rest.controller;

import com.gamereview.api.entities.Game;
import com.gamereview.api.entities.dto.GameDropdownDTO;
import com.gamereview.api.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins ="http://localhost:4200")
public class GameController {

    private final ModelMapper modelMapper;
    private final GameService gameService;

    public GameController(GameService gameService, ModelMapper modelMapper) {
        this.gameService = gameService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/image/{id}")
    public ResponseEntity uploadImage(@PathVariable Integer id, @RequestParam("file") MultipartFile multipartFile, @RequestParam("imageType") String imageType) throws IOException {
        gameService.uploadImage(id, multipartFile, imageType);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/image/{gameID}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable Integer gameID) throws IOException {
        ResponseInputStream<GetObjectResponse> response = gameService.getImage(gameID);
        return IOUtils.toByteArray(response);
    }

    @GetMapping
    @Operation(tags = {"Game"}, summary = "Find all games", description = "Find all games")
    public List<Game> findAll() {
        return gameService.findAllGames();
    }

    @GetMapping("/list")
    @Operation(tags = {"Game"}, summary = "Find all games for the dropdown", description = "Find all games for the dropdown")
    public List<GameDropdownDTO> findAllGamesDropdown() {

        return gameService.findAllGamesDropdown()
                .stream().map(game -> modelMapper.map(game, GameDropdownDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(tags = {"Game"}, summary = "Find game by id", description = "Find game by id")
    public ResponseEntity<Game> findGameById(@PathVariable Integer id){
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
    public ResponseEntity<Game> updateGame(@PathVariable Integer id, @RequestBody Game game){
        if(gameService.findGameById(id) == null){
            return ResponseEntity.notFound().build();
        }else{
            game = gameService.updateGame(id, game);
            return ResponseEntity.ok().body(game);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(tags = {"Game"}, summary = "Delete game", description = "Delete game by id")
    public ResponseEntity<Void> deleteGame(@PathVariable Integer id){
        if(gameService.findGameById(id) == null){
            return ResponseEntity.notFound().build();
        }else{
            gameService.deleteGame(id);
            return ResponseEntity.noContent().build();
        }
    }

}
