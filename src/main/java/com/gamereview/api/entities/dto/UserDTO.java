package com.gamereview.api.entities.dto;

import com.gamereview.api.enumaration.PermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private PermissionEnum permission;
    private List<GameDTO> games = new ArrayList<>();
}
