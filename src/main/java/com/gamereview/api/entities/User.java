package com.gamereview.api.entities;

import com.gamereview.api.enumaration.PermissionEnum;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message="Campo nome é obrigatório")
    private String name;

    @NotEmpty(message="Campo username é obrigatório")
    private String username;

    @NotEmpty(message="Campo email é obrigatório")
    private String email;

    @NotEmpty(message="Campo senha é obrigatório")
    private String password;

    private PermissionEnum permission;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "tb_user_game",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private List<Game> games = new ArrayList<>();
}
