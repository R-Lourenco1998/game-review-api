package com.gamereview.api.entities.dto;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class GameDTO {

    private Integer id;
    private String name;
    @Lob
    private String description;
    private List<Integer> genres;
    private List<String> genreNames;
    private LocalDate releaseDate;
    private String developer;
    private List<Integer> platforms;
    private List<String> platformNames;
    private String publisher;
    private String imageUrl;
    private String imageCoverUrl;

    public void setGenreNames(List<String> genreNames) {
        this.genreNames = genreNames;
    }

    public void setPlatformNames(List<String> platformNames) {
        this.platformNames = platformNames;
    }
}
