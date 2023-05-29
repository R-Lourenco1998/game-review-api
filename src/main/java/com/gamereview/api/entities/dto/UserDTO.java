package com.gamereview.api.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gamereview.api.enumaration.PermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private PermissionEnum permission;
    private List<GameDTO> games = new ArrayList<>();
}
