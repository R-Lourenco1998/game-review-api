package com.gamereview.api.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.gamereview.api.entities.Game;
import com.gamereview.api.entities.dto.GameDTO;
import com.gamereview.api.enumaration.GenreEnum;
import com.gamereview.api.enumaration.PlatformEnum;
import com.gamereview.api.exceptions.FileExtensionInvalidException;
import com.gamereview.api.mapper.GameMapper;
import com.gamereview.api.repositories.GameRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
    public void uploadImageList(Integer id, MultipartFile multipartFile){
        try {

            Game game = findGameById(id);
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
                fileName = "games/" + game.getName() + ".png";

            s3client.putObject(bucketName, fileName, multipartFile.getInputStream(), null);
            URL url = s3client.getUrl(bucketName, fileName);
                game.setImageUrl(url.toString());
                gameRepository.save(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void uploadImageCover(Integer id, MultipartFile multipartFile){
        try {

            Game game = findGameById(id);
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
                fileName = "cover/" + game.getName() + ".jpg";

            s3client.putObject(bucketName, fileName, multipartFile.getInputStream(), null);
            URL url = s3client.getUrl(bucketName, fileName);
                game.setImageCoverUrl(url.toString());
                gameRepository.save(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResponseInputStream<GetObjectResponse> getImage(Integer gameID){
        Game game = findGameById(gameID);
        try{
            return s3Client.getObject(GetObjectRequest
                    .builder()
                    .bucket(bucketName)
                    .key("games/"+game.getName())
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
//        Game game = gameMapper.toEntity(gameDTO);
//        game.setName(gameDTO.getName());
//        game.setDescription(gameDTO.getDescription());
//        game.setPlatforms(getPlatformsById(gameDTO.getPlatformIds()));
//        game.setGenres(getGenreById(gameDTO.getGenreIds()));
//        game.setDeveloper(gameDTO.getDeveloper());
//        game.setPublisher(gameDTO.getPublisher());
//        game.setReleaseDate(gameDTO.getReleaseDate());
//        game.setImageCoverUrl(gameDTO.getImageCoverUrl());
//        game.setImageUrl(gameDTO.getImageUrl());
        //return this.gameRepository.save(game);
        System.out.println("id: " + save.getId());
        return gameMapper.toDTO(gameRepository.save(save));
    }


    private List<GenreEnum> getGenreById(List<Integer> genreIds) {
        return genreIds.stream().map(GenreEnum::fromId).collect(Collectors.toList());
    }

    private List<PlatformEnum> getPlatformsById(List<Integer> platformsIds) {
        return platformsIds.stream().map(PlatformEnum::fromId).collect(Collectors.toList());
    }

    public List<Game> findAllGamesDropdown() {
        return this.gameRepository.findAll();
    }

    public Game findGameById(Integer id) {
        Optional<Game> game = this.gameRepository.findById(id);
        return game.orElse(null);
    }

    public List<Game> findAllGames() {
        return this.gameRepository.findAll();
    }

    public Game updateGame(Integer id, Game game) {
        Game obj = findGameById(id);
        obj.setName(game.getName());
        obj.setDescription(game.getDescription());
        obj.setPlatforms(game.getPlatforms());
        obj.setGenres(game.getGenres());
        obj.setReleaseDate(game.getReleaseDate());
        obj.setDeveloper(game.getDeveloper());
        obj.setPublisher(game.getPublisher());
        obj.setImageUrl(game.getImageUrl());
        return this.gameRepository.save(game);
    }

    public void deleteGame(Integer id) {
        this.gameRepository.deleteById(id);
    }
}
