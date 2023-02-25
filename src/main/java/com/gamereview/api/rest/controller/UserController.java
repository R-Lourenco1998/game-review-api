package com.gamereview.api.rest.controller;

import com.gamereview.api.entities.User;
import com.gamereview.api.entities.dto.UserDTO;
import com.gamereview.api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins ="http://localhost:4200")
@AllArgsConstructor
public class UserController {

    private ModelMapper modelMapper;

    private final UserService userService;

    @GetMapping
    @Operation(tags = {"User"}, summary = "Find all users", description = "Find all users")
    public List<UserDTO> findAll() {
        return userService.findAllUser().stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(tags = {"User"}, summary = "Find user by id", description = "Find user by id")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    @Operation(tags = {"User"}, summary = "Create user", description = "Create user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user = userService.createUser(user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    @Operation(tags = {"User"}, summary = "Update user", description = "Update user by id and body")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (userService.findUserById(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            user = userService.updateUser(id, user);
            return ResponseEntity.ok().body(user);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(tags = {"User"}, summary = "Delete user", description = "Delete user by id")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findUserById(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
    }

}
