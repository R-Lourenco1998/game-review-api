package com.gamereview.api.db;

import com.gamereview.api.entities.Game;
import com.gamereview.api.repositories.GameRepository;
import com.gamereview.api.repositories.UserRepository;
import com.gamereview.api.services.GameService;
import com.gamereview.api.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Profile("db-dev")
@Slf4j
@Component
public class DbLoader implements CommandLineRunner {

    private UserRepository userRepository;

    private UserService userService;

    private GameService gameService;
    private GameRepository gameRepository;

    public DbLoader(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Populating db with Users and Games");

        Game game1 = new Game();
        game1.setName("The Last of Us Part 1");
        game1.setGenre("Action");
        game1.setPlatform("Playstation 5");
        game1.setReleaseDate(LocalDate.of(2022, 9, 2));
        game1.setDeveloper("Naughty Dog");
        game1.setPublisher("Sony Interactive Entertainment");
        game1.setDescription("The Last of Us Part I conta a história de Joel, um sobrevivente abatido que perdeu a filha no início de uma pandemia devastadora que dizimou a população e é contratado para tirar uma adolescente corajosa e madura de apenas 14 anos, chamada Ellie, de uma zona de quarentena militar. Porém, o que começa como um pequeno serviço se transforma em uma jornada brutal pelos Estados Unidos. Já a campanha Left Behind explora os eventos que mudaram para sempre as vidas de Ellie e sua melhor amiga, Riley, e combina temas de sobrevivência, lealdade e amor com momentos intensos de ação.");
        game1.setImageUrl("https://game-review-java.s3.sa-east-1.amazonaws.com/The%20Last%20of%20Us%20Part%201.jpg");

        Game game2 = new Game();
        game2.setName("God of War Ragnarök");
        game2.setGenre("Action");
        game2.setPlatform("Playstation 5");
        game2.setReleaseDate(LocalDate.of(2022, 11, 9));
        game2.setDeveloper("Sony Santa Monica");
        game2.setPublisher("Sony Interactive Entertainment");
        game2.setDescription("Kratos e Atreus devem viajar pelos Nove Reinos em busca de respostas enquanto as forças asgardianas se preparam para uma batalha profetizada que causará o fim do mundo. Nessa jornada, eles explorarão paisagens míticas impressionantes e enfrentarão inimigos aterradores: deuses nórdicos e monstros.");
        game2.setImageUrl("https://game-review-java.s3.sa-east-1.amazonaws.com/God%20of%20War%20Ragnar%C3%B6k.jpg");
        gameRepository.save(game1);
        gameRepository.save(game2);
    }

}
