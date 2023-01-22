package com.gamereview.api.services.impl;

import com.gamereview.api.entities.User;
import com.gamereview.api.repositories.UserRepository;
import com.gamereview.api.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user){
        return this.userRepository.save(user);
    }

    @Override
    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id){
        this.userRepository.deleteById(id);
    }

    //Depois adicionarei exceções personalizadas aqui
    @Override
    public User findUserById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public User updateUser(Long id, User user){
        User obj = findUserById(id);
        obj.setName(user.getName());
        obj.setEmail(user.getEmail());
        obj.setPassword(user.getPassword());
        return this.userRepository.save(user);
    }
}
