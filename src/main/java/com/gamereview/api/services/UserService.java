package com.gamereview.api.services;

import com.gamereview.api.entities.User;
import com.gamereview.api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user){
      return this.userRepository.save(user);
    }

    public List<User> findAllUser(){
      return this.userRepository.findAll();
    }

    public void deleteUser(Long id){
      this.userRepository.deleteById(id);
    }

    //Depois adicionarei exceções personalizadas aqui
    public User findUserById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElse(null);
    }

    public User updateUser(Long id, User user){
        User obj = findUserById(id);
        obj.setName(user.getName());
        obj.setEmail(user.getEmail());
        obj.setPassword(user.getPassword());
        return this.userRepository.save(user);
    }

}
