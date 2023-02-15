package com.gamereview.api.rest.controller;

import com.gamereview.api.entities.Game;
import com.gamereview.api.entities.dto.GameDTO;
import com.gamereview.api.entities.dto.GameDropdownDTO;
import com.gamereview.api.entities.dto.GenreDTO;
import com.gamereview.api.entities.dto.PlatformDTO;
import com.gamereview.api.enumaration.GenreEnum;
import com.gamereview.api.enumaration.PlatformEnum;
import com.gamereview.api.mapper.GameMapper;
import com.gamereview.api.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins ="http://localhost:4200")
@AllArgsConstructor
public class GameController {

    private final ModelMapper modelMapper;
    private final GameService gameService;
    private final GameMapper mapper;

    @GetMapping("/platforms")
    public ResponseEntity<List<PlatformDTO>> getPlatforms() {
        List<PlatformDTO> platforms = new ArrayList<>();
        for (PlatformEnum platform : PlatformEnum.values()) {
            platforms.add(new PlatformDTO(platform.getId(), platform.getName()));
        }
        return ResponseEntity.ok().body(platforms);
    }

    @GetMapping("/genres")
    public ResponseEntity<List<GenreDTO>> getGenres() {
        List<GenreDTO> genres = new ArrayList<>();
        for (GenreEnum genre : GenreEnum.values()) {
            genres.add(new GenreDTO(genre.getId(), genre.getName()));
        }
        return ResponseEntity.ok().body(genres);
    }

    @PostMapping("/image/list/{id}")
    @Operation(tags = {"Game"}, summary = "Create game with image to list of games", description = "image of the game to display on game list")
    public ResponseEntity uploadImageList(@PathVariable Integer id, @RequestParam("imageList") MultipartFile multipartFile) throws IOException {
        gameService.uploadImageList(id, multipartFile);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/image/cover/{id}")
    @Operation(tags = {"Game"}, summary = "Create game with imagem cover", description = "image cover of the game to display on game page")
    public ResponseEntity uploadImageCover(@PathVariable Integer id, @RequestParam("imageCover") MultipartFile multipartFile) throws IOException {
        gameService.uploadImageCover(id, multipartFile);
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    @Operation(tags = {"Game"}, summary = "Create game", description = "Create game")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<GameDTO> createGame(@RequestBody GameDTO dto){
        GameDTO gameDTO = this.gameService.createGame(dto);
//        System.out.println("id: " + gameDTO.getId());
//        System.out.println("Name: " + gameDTO.getName());
        //Game game = gameService.createGame(gameDTO);
        return new ResponseEntity<>(gameDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable Integer id) throws IOException {
        ResponseInputStream<GetObjectResponse> response = gameService.getImage(id);
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
    public ResponseEntity<GameDTO> findGameById(@PathVariable Integer id){
        Game game = gameService.findGameById(id);
        GameDTO gameDTO = mapper.toDTO(game);
        if(game == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(gameDTO);
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
