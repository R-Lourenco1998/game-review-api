package com.gamereview.api.enumaration;

public enum GenreEnum {

    ACTION(1, "Ação"),
    ADVENTURE(2, "Aventura"),
    RPG(3, "RPG"),
    SOULS_LIKE(4, "Souls Like"),
    ACTION_ADVENTURE(5, "Ação e Aventura"),
    PUZZLE(6, "Puzzle"),
    MMO(7, "MMO"),
    SPORTS(8, "Esportes"),

    STRATEGY(9, "Estratégia"),
    SIMULATION(10, "Simulação"),
    RACING(11, "Corrida"),
    FIGHTING(12, "Luta"),
    SHOOTER(13, "Tiro"),
    HORROR(14, "Terror"),
    PLATFORMER(15, "Plataforma"),
    ARCADE(16, "Arcade"),
    CASUAL(17, "Casual"),
    INDIE(18, "Indie"),
    FAMILY(19, "Família"),
    VIRTUAL_REALITY(20, "Realidade Virtual"),

    RHYTHM_GAMES(21, "Ritmo"),

    OPEN_WORLD(22, "Mundo Aberto"),

    ROGUE_LIKE(23, "Rogue Like"),

    HACK_AND_SLASH(24, "Hack and Slash"),

    ZUMBI(25, "Zumbi");

    private final int id;
    private final String name;

    GenreEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public static String getNameById(int id) {
        for (GenreEnum genre : GenreEnum.values()) {
            if (genre.getId() == id) {
                return genre.getName();
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
}