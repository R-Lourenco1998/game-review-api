package com.gamereview.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_game")
public class Game implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Lob
    private String description;

    private String genre;

    //transformar platform em array de enums
    private String platform;

    private LocalDate releaseDate;

    private String developer;

    private String publisher;

    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Column(name = "image_cover_url", length = 256)
    private String imageCoverUrl;
}
