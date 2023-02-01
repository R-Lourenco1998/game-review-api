package com.gamereview.api.services;
import com.gamereview.api.entities.Game;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.util.List;

public interface GameService {

    List<Game> findAllGames();

    Game createGame(Game game);

    Game findGameById(Integer id);

    Game updateGame(Integer id, Game game);

    void deleteGame(Integer id);

    List<Game> findAllGamesDropdown();

    void uploadImage(Integer id, MultipartFile multipartFile) throws IOException;

    ResponseInputStream <GetObjectResponse> getImage(Integer gameID);
}
