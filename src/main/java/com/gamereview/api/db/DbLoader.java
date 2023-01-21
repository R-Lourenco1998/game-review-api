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

        Game Game1 = new Game();
        Game1.setName("The Last of Us Part 1");
        Game1.setGenre("Action");
        Game1.setPlatform("Playstation 5");
        Game1.setReleaseDate(LocalDate.of(2022, 9, 2));
        Game1.setDeveloper("Naughty Dog");
        Game1.setPublisher("Sony Interactive Entertainment");
        Game1.setDescription("The Last of Us Part I conta a história de Joel, um sobrevivente abatido que perdeu a filha no início de uma pandemia devastadora que dizimou a população");
        gameRepository.save(Game1);
    }

}
