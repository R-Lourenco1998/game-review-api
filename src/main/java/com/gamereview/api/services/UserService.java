package com.gamereview.api.services;

import com.gamereview.api.entities.Game;
import com.gamereview.api.entities.User;
import com.gamereview.api.entities.dto.UserDTO;
import com.gamereview.api.exceptions.InvalidPasswordException;
import com.gamereview.api.exceptions.UserNotFoundException;
import com.gamereview.api.mapper.GameMapper;
import com.gamereview.api.mapper.UserMapper;
import com.gamereview.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final GameService gameService;
    private final GameMapper gameMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }

    public void authenticate(User user) {
        UserDetails userFound = loadUserByUsername(user.getUsername());

        if (userFound == null) {
            throw new UserNotFoundException("Usuário" + user.getUsername() + "não encontrado, tente novamente!");
        }

        boolean passwordMatches = encoder.matches(user.getPassword(), userFound.getPassword());

        if (!passwordMatches) {
            throw new InvalidPasswordException("Senha inválida, tente novamente!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("Usuário " + username + " não encontrado, tente novamente!");
        }
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getPermission().toString())
                .build();
    }

    public UserDTO findUserByUsername(String username) {
        return modelMapper.map(userRepository.findByUsername(username), UserDTO.class);
    }

    @Transactional
    public Page<UserDTO> findAllUser(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDTO);
    }

    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    //Depois adicionarei exceções personalizadas aqui
    public UserDTO findUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Usuário não localizado - id: " + id));

    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserDTO obj = findUserById(id);
        obj.setName(userDTO.getName());
        obj.setUsername(userDTO.getUsername());
        obj.setEmail(userDTO.getEmail());
        obj.setPermission(userDTO.getPermission());
        obj.setGames(userDTO.getGames());
        //obj.setPassword(user.getPassword());
        User user = userMapper.toEntity(obj);
        this.userRepository.save(user);
        return obj;
    }

    @Transactional
    public void addGameToUser(Long userId, Long gameId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("Usuário não encontrado com o id: " + userId));
        Game game = gameMapper.toEntity(gameService.findGameById(gameId));
        if (game != null) {
            user.getGames().add(game);
            userRepository.save(user);
            gameService.addUserToGame(user, game);
        } else {
            throw new NoSuchElementException("Jogo não encontrado com o id " + gameId);
        }
    }
}
