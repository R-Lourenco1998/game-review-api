package com.gamereview.api.services;

import com.gamereview.api.entities.Game;
import com.gamereview.api.entities.User;
import com.gamereview.api.entities.dto.GameDTO;
import com.gamereview.api.entities.dto.UserDTO;
import com.gamereview.api.enumaration.GenreEnum;
import com.gamereview.api.enumaration.PermissionEnum;
import com.gamereview.api.enumaration.PlatformEnum;
import com.gamereview.api.mapper.GameMapper;
import com.gamereview.api.mapper.UserMapper;
import com.gamereview.api.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    private GameService gameService;
    @Mock
    private GameMapper gameMapper;
    @Mock
    private UserMapper userMapper;
    private PasswordEncoder encoder;
    @Mock
    private ModelMapper modelMapper;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        startUser();
    }

    @Test
    void createUser() {
    }

    @Test
    void authenticate() {
    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void findUserByUsername() {
    }

    @Test
    void findAllUser() {
    }

    @Test
    void testDeleteUser() {
        // Executa o método de teste
        userService.deleteUser(user.getId());

        // Verifica se o método deleteById foi chamado uma vez com o argumento 1L
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void testFindUserById() {
        // Mock do UserRepository
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user)); // Retorna o Optional com o usuário salvo no repositório

        // Mock do UserMapper
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        // Executa o método de teste
        UserDTO result = userService.findUserById(user.getId());

        // Verificações
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPermission(), result.getPermission());
        assertEquals(user.getPassword(), result.getPassword());
        //Assertions.assertIterableEquals(user.getGames().stream().map(gameMapper::toDTO).collect(Collectors.toList()), result.getGames()); // Mapeia os jogos para GameDTO antes de comparar

        List<Game> expectedGames = user.getGames();
        List<GameDTO> actualGames = result.getGames();

        assertEquals(expectedGames.size(), actualGames.size());

        for (int i = 0; i < expectedGames.size(); i++) {
            Game expectedGame = expectedGames.get(i);
            GameDTO actualGame = actualGames.get(i);

            assertEquals(expectedGame.getId(), actualGame.getId());
            assertEquals(expectedGame.getName(), actualGame.getName());
            assertEquals(expectedGame.getPublisher(), actualGame.getPublisher());
            assertEquals(expectedGame.getGenres(), actualGame.getGenres());
            assertEquals(expectedGame.getPlatforms(), actualGame.getPlatforms());
            assertEquals(expectedGame.getReleaseDate(), actualGame.getReleaseDate());
            assertEquals(expectedGame.getDescription(), actualGame.getDescription());
            assertEquals(expectedGame.getDeveloper(), actualGame.getDeveloper());
        }

        // Verifica se o método findById foi chamado uma vez com o argumento 1L
        verify(userRepository, times(1)).findById(user.getId());

        // Verifica se o método toDTO foi chamado uma vez com o objeto user
        verify(userMapper, times(1)).toDTO(user);
    }


    @Test
    void updateUser() {
    }

    @Test
    void addGameToUser() {
    }

    private void startUser() {
        user = new User();
        user.setId(1L);
        user.setUsername("fulano");
        user.setName("Fulano da Silva");
        user.setPassword("1234");
        user.setEmail("fulano@gmail.com");
        user.setPermission(PermissionEnum.ADMIN);

        //Criando lista de jogos
        List<Game> games = new ArrayList<>();
        Game game1 = new Game();
        game1.setId(1L);
        game1.setName("GTA V");
        game1.setPublisher("Rockstar Games");
        game1.setDescription("Grand Theft Auto V é um jogo eletrônico de ação-aventura desenvolvido pela Rockstar North e publicado pela Rockstar Games.");
        game1.setDeveloper("Rockstar North");
        game1.setImageUrl("https://upload.wikimedia.org/wikipedia/pt/a/a5/Grand_Theft_Auto_V_capa.png");
        game1.setImageCoverUrl("https://upload.wikimedia.org/wikipedia/pt/a/a5/Grand_Theft_Auto_V_capa.png");
        game1.setReleaseDate(LocalDate.of(2013, 9, 17));

        //setando genêros
        List<Integer> genresIds = List.of(
                GenreEnum.ACTION.getId(),
                GenreEnum.ACTION_ADVENTURE.getId(),
                GenreEnum.OPEN_WORLD.getId()
        );
        game1.setGenres(genresIds);

        //setando plataformas
        List<Integer> platformsIds = List.of(
                PlatformEnum.PC.getId(),
                PlatformEnum.PS4.getId(),
                PlatformEnum.XBOX_ONE.getId()
        );
        game1.setPlatforms(platformsIds);
        games.add(game1);
        user.setGames(games);
        //game1.setUsers(List.of(user));

        //mock userDTO
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("fulano");
        userDTO.setName("Fulano da Silva");
        userDTO.setPassword("1234");
        userDTO.setEmail("fulano@gmail.com");
        userDTO.setPermission(PermissionEnum.ADMIN);
        userRepository.save(user);

        //mock gameDTO
        GameDTO gameDTO1 = new GameDTO();
        gameDTO1.setId(1L);
        gameDTO1.setName("GTA V");
        gameDTO1.setPublisher("Rockstar Games");
        gameDTO1.setDescription("Grand Theft Auto V é um jogo eletrônico de ação-aventura desenvolvido pela Rockstar North e publicado pela Rockstar Games.");
        gameDTO1.setDeveloper("Rockstar North");
        gameDTO1.setImageUrl("https://upload.wikimedia.org/wikipedia/pt/a/a5/Grand_Theft_Auto_V_capa.png");
        gameDTO1.setImageCoverUrl("https://upload.wikimedia.org/wikipedia/pt/a/a5/Grand_Theft_Auto_V_capa.png");
        gameDTO1.setReleaseDate(LocalDate.of(2013, 9, 17));
        gameDTO1.setGenres(genresIds);
        gameDTO1.setPlatforms(platformsIds);

        List<GameDTO> gamesDTO = new ArrayList<>();
        gamesDTO.add(gameDTO1);
        userDTO.setGames(gamesDTO);
    }

}