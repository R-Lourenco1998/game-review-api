package com.gamereview.api.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.gamereview.api.entities.Game;
import com.gamereview.api.entities.User;
import com.gamereview.api.entities.dto.GameDTO;
import com.gamereview.api.enumaration.GenreEnum;
import com.gamereview.api.enumaration.PlatformEnum;
import com.gamereview.api.exceptions.FileExtensionInvalidException;
import com.gamereview.api.mapper.GameMapper;
import com.gamereview.api.repositories.GameRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final S3Client s3Client;
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.s3.accessKey}")
    private String accessKey;
    @Value("${aws.s3.secretKey}")
    private String secretKey;
    public void uploadImageList(Long id, MultipartFile multipartFile){
        try {

            GameDTO gameDTO = findGameById(id);
            String fileName = multipartFile.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            assert extension != null;
            if(!extension.equals("png")){
                throw new FileExtensionInvalidException("Extensão de arquivo inválida: " + extension + ". Envie apenas png.");
            }
            AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                    .withRegion(Regions.SA_EAST_1)
                    .build();
                fileName = "games/" + gameDTO.getName() + ".png";

            s3client.putObject(bucketName, fileName, multipartFile.getInputStream(), null);
            URL url = s3client.getUrl(bucketName, fileName);
            gameDTO.setImageUrl(url.toString());
                gameRepository.save(gameMapper.toEntity(gameDTO));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void uploadImageCover(Long id, MultipartFile multipartFile){
        try {

            GameDTO gameDTO = findGameById(id);
            String fileName = multipartFile.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            assert extension != null;
            if(!extension.equals("jpeg") && !extension.equals("jpg")){
                throw new FileExtensionInvalidException("Extensão de arquivo inválida: " + extension + ". Envie apenas jpg ou jpeg.");
            }
            AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                    .withRegion(Regions.SA_EAST_1)
                    .build();
                fileName = "cover/" + gameDTO.getName() + ".jpg";

            s3client.putObject(bucketName, fileName, multipartFile.getInputStream(), null);
            URL url = s3client.getUrl(bucketName, fileName);
            gameDTO.setImageCoverUrl(url.toString());
                gameRepository.save(gameMapper.toEntity(gameDTO));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResponseInputStream<GetObjectResponse> getImage(Long gameID){
        GameDTO gameDTO = findGameById(gameID);
        try{
            return s3Client.getObject(GetObjectRequest
                    .builder()
                    .bucket(bucketName)
                    .key("games/"+gameDTO.getName())
                    .build());
        }catch (NoSuchKeyException e){
            return s3Client.getObject(GetObjectRequest
                    .builder()
                    .bucket(bucketName)
                    .key("games/default.jpg")
                    .build());
        }

    }
    public GameDTO createGame(GameDTO gameDTO) {
        Game save = gameMapper.toEntity(gameDTO);
        return gameMapper.toDTO(gameRepository.save(save));
    }
    public List<Game> findAllGamesDropdown() {
        return this.gameRepository.findAll();
    }

    public GameDTO findGameById(Long id) {

        if(this.gameRepository.findById(id).isPresent()){
            Game game = this.gameRepository.findById(id).get();
            GameDTO gameDTO = gameMapper.toDTO(game);
            gameDTO.setGenreNames(game.getGenres().stream().map(GenreEnum::getNameById).collect(Collectors.toList()));
            gameDTO.setPlatformNames(
                    game.getPlatforms().stream().map(PlatformEnum::getNameById).collect(Collectors.toList()));
            return gameDTO;
        }
        return null; //TODO: Criar uma exceção para quando o jogo não for encontrado
    }

    public Page<GameDTO> findAllGames(Pageable pageable) {
        Page<Game> savedGames = this.gameRepository.findAll(pageable);
        Page<GameDTO> gameDTOPage = savedGames.map(gameMapper::toDTO);

        gameDTOPage.forEach(gameDTO -> {
            List<String> genreNames = new ArrayList<>();
            for (Integer genreId : gameDTO.getGenres()) {
                String genreName = GenreEnum.getNameById(genreId);
                if (genreName != null) {
                    genreNames.add(genreName);
                }
            }
            gameDTO.setGenreNames(genreNames);

            List<String> platformNames = new ArrayList<>();
            for (Integer platformId : gameDTO.getPlatforms()) {
                String platformName = PlatformEnum.getNameById(platformId);
                if (platformName != null) {
                    platformNames.add(platformName);
                }
            }
            gameDTO.setPlatformNames(platformNames);
        });
        return gameDTOPage;
    }


    public GameDTO updateGame(Long id, GameDTO game) {
        GameDTO obj = findGameById(id);
        obj.setName(game.getName());
        obj.setDescription(game.getDescription());
        obj.setPlatforms(game.getPlatforms());
        obj.setGenres(game.getGenres());
        obj.setReleaseDate(game.getReleaseDate());
        obj.setDeveloper(game.getDeveloper());
        obj.setPublisher(game.getPublisher());
        obj.setImageUrl(game.getImageUrl());
        this.gameRepository.save(gameMapper.toEntity(obj));
        return obj;
    }

    public void deleteGame(Long id) {
        this.gameRepository.deleteById(id);
    }

    @Transactional
    public void addUserToGame(User user, Game game) {
        game.getUsers().add(user);
        gameRepository.save(game);
    }

}
