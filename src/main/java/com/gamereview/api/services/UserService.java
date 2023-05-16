package com.gamereview.api.services;

import com.amazonaws.services.dlm.model.ResourceNotFoundException;
import com.gamereview.api.entities.Game;
import com.gamereview.api.entities.User;
import com.gamereview.api.entities.dto.UserDTO;
import com.gamereview.api.mapper.GameMapper;
import com.gamereview.api.mapper.UserMapper;
import com.gamereview.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final GameService gameService;

    private final GameMapper gameMapper;

    private final UserMapper userMapper;

    public void createUser(User user){
        userRepository.save(user);
    }

    @Transactional
    public Page<UserDTO> findAllUser(Pageable pageable){
        return userRepository.findAll(pageable).map(userMapper::toDTO);
    }

    public void deleteUser(Long id){
        this.userRepository.deleteById(id);
    }

    //Depois adicionarei exceções personalizadas aqui
    public UserDTO findUserById(Long id){
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Usuário não localizado - id: " + id));

    }

    public UserDTO updateUser(Long id, UserDTO userDTO){
        UserDTO obj = findUserById(id);
        obj.setName(userDTO.getName());
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
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Game game = gameMapper.toEntity(gameService.findGameById(gameId));
        if(game != null){
            user.getGames().add(game);
            userRepository.save(user);
            gameService.addUserToGame(user, game);
        }else {
            throw new ResourceNotFoundException("Game not found with id " + gameId);
        }
    }
}
