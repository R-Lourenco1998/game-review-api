package com.gamereview.api.repositories;

import com.gamereview.api.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u JOIN FETCH u.games WHERE u.id = :id")
    Optional<User> findUserWithGamesById(Long id);

//    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.games")
//    Page<User> findAllWithGames(Pageable pageable);
}
