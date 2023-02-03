package com.gamereview.api.services.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.gamereview.api.entities.Game;
import com.gamereview.api.exceptions.FileExtensionInvalidException;
import com.gamereview.api.repositories.GameRepository;
import com.gamereview.api.services.GameService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
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

@Service
public class GameServiceImpl implements GameService {

    private final S3Client s3Client;

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository, S3Client s3Client) {
        this.gameRepository = gameRepository;
        this.s3Client = s3Client;
    }
    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.s3.accessKey}")
    private String accessKey;
    @Value("${aws.s3.secretKey}")
    private String secretKey;
    public void uploadImage(Integer id, MultipartFile multipartFile, String imageType){
        try {

            Game game = findGameById(id);
            String fileName = multipartFile.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            assert extension != null;
            if(!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")){
                throw new FileExtensionInvalidException("Extensão de arquivo inválida: " + extension + ". Envie apenas jpg, jpeg ou png.");
            }
            AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                    .withRegion(Regions.SA_EAST_1)
                    .build();

            if(imageType.equals("cover")) {
                fileName = "cover/" + game.getName() + ".jpg";
            }else if(imageType.equals("game")){
                fileName = "games/" + game.getName() + ".png";
            }
            //fileName = "games/"+game.getName() + ".jpg";
            s3client.putObject(bucketName, fileName, multipartFile.getInputStream(), null);
            URL url = s3client.getUrl(bucketName, fileName);
            if(imageType.equals("cover")){
                game.setImageCoverUrl(url.toString());
            } else if(imageType.equals("game")){
                game.setImageUrl(url.toString());
            }
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

    public Game createGame(Game game) {
        return this.gameRepository.save(game);
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
        obj.setGenre(game.getGenre());
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
