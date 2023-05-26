package com.gamereview.api.rest.controller;

import com.gamereview.api.entities.User;
import com.gamereview.api.entities.dto.UserDTO;
import com.gamereview.api.exceptions.PasswordInvalidException;
import com.gamereview.api.rest.controller.dto.CredentialsDTO;
import com.gamereview.api.rest.controller.dto.TokenDTO;
import com.gamereview.api.security.jwt.JwtService;
import com.gamereview.api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @GetMapping
    @Operation(tags = {"User"}, summary = "Find all users", description = "Find all users")
    public ResponseEntity<Page<UserDTO>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<UserDTO> userDTOPage = this.userService.findAllUser(PageRequest.of(page, size));
        return new ResponseEntity<>(userDTOPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(tags = {"User"}, summary = "Find user by id", description = "Find user by id")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO userDTO = userService.findUserById(id);
        if (userDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping()
    @Operation(tags = {"User"}, summary = "Create user", description = "Create user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userService.createUser(user);
    }

    @PostMapping("/auth")
    @Operation(tags = {"User"}, summary = "Authenticate user", description = "Authenticate user")
    public TokenDTO authenticate(@RequestBody CredentialsDTO credentialsDTO) {
        try {
            User user = User.builder()
                    .username(credentialsDTO.getUsername())
                    .password(credentialsDTO.getPassword()).build();

            // Autenticar o usuário e gerar o token
            userService.authenticate(user);
            String token = jwtService.generateToken(user);

            // Retornar o token
            return new TokenDTO(user.getUsername(), token);
        } catch (UsernameNotFoundException | PasswordInvalidException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(tags = {"User"}, summary = "Update user", description = "Update user by id and body")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        if (userService.findUserById(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            userDTO = userService.updateUser(id, userDTO);
            return ResponseEntity.ok().body(userDTO);
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

    @PutMapping("/{userId}/game/{gameId}")
    @Operation(tags = {"User"}, summary = "Add game to user", description = "Add game to user")
    public ResponseEntity<Void> addGameToUser(@PathVariable Long userId, @PathVariable Long gameId) {
        userService.addGameToUser(userId, gameId);
        return ResponseEntity.noContent().build();
    }

}
