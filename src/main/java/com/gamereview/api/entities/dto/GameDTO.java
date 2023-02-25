package com.gamereview.api.entities.dto;

import com.gamereview.api.enumaration.GenreEnum;
import com.gamereview.api.enumaration.PlatformEnum;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class GameDTO {

    private Integer id;
    private String name;

    @Lob
    private String description;

//    @ElementCollection
//    @Enumerated(EnumType.STRING)
    private List<Integer> genres;

    private List<String> genreNames;

    private LocalDate releaseDate;

    private String developer;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<PlatformEnum> platforms;

    private String publisher;

    private String imageUrl;
    private String imageCoverUrl;

//    public List<Integer> getPlatformIds() {
//        List<Integer> platformsIds = new ArrayList<>();
//        for (PlatformEnum platform : platforms) {
//            platformsIds.add(platform.getId());
//        }
//        return platformsIds;
//    }
//
//    public List<Integer> getGenreIds() {
//        List<Integer> genreIds = new ArrayList<>();
//        for (GenreEnum genre : genres) {
//            genreIds.add(genre.getId());
//        }
//        return genreIds;
//    }

    public void setGenreNames(List<String> genreNames) {
        this.genreNames = genreNames;
    }
}
